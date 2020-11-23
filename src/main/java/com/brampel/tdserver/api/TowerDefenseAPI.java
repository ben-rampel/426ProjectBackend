package com.brampel.tdserver.api;

import com.brampel.tdserver.auth.JwtTokenUtil;
import com.brampel.tdserver.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

@RestController
public class TowerDefenseAPI {
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public TowerDefenseAPI(UserRepository userRepository, ScoreRepository scoreRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil){
        this.userRepository = userRepository;
        this.scoreRepository = scoreRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/registerAccount", method= RequestMethod.POST )
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest request){
        //escape html
        request.setUsername(escapeHtml4(request.getUsername()));

        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body(new GenericResponse("","Username in use"));
        }

        userRepository.save(new User(request.getUsername(), passwordEncoder.encode(request.getPassword())));
        return ResponseEntity.ok(new GenericResponse("Success",""));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/checkToken", method= RequestMethod.POST )
    public ResponseEntity<?> getUserFromToken(@RequestBody TokenReq s) {
        String username = jwtTokenUtil.getUsernameFromToken(s.getToken());
        Long id = userRepository.findByUsername(username).getId();
        return ResponseEntity.ok(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/users", method= RequestMethod.GET )
    public ResponseEntity<?> getUsers(){
        List<PublicUser> users = userRepository.findAll().stream()
                .map(u -> new PublicUser(u,
                        scoreRepository.findAllByUser(u).stream().map(PublicScore::new).collect(Collectors.toList())
                )).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/users/{id}", method= RequestMethod.GET )
    public ResponseEntity<?> getUser(@PathVariable("id") String id){
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if(user.isPresent()){
            PublicUser publicUser = new PublicUser(user.get(),
                    scoreRepository.findAllByUser(user.get()).stream().map(PublicScore::new).collect(Collectors.toList())
            );
            return ResponseEntity.ok(publicUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/users/{id}", method= RequestMethod.PUT )
    @PreAuthorize("#id.equals(#p.IDasString)")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request, @AuthenticationPrincipal TDUserDetails p){
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if(user.isPresent()){
            User update = user.get();
            if(request.getUsername() != null) update.setUsername(request.getUsername());
            if(request.getPassword() != null) update.setPassword(passwordEncoder.encode(request.getPassword()));
            if(request.getAvatarURL() != null) update.setAvatarURL(request.getAvatarURL());
            userRepository.save(update);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/users/{id}", method= RequestMethod.DELETE )
    @PreAuthorize("#id.equals(#p.IDasString)")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id, @AuthenticationPrincipal TDUserDetails p){
        scoreRepository.findAll().forEach((v) ->{
            if(v.getUser().getId() == Long.parseLong(id)) {
                scoreRepository.deleteById(v.getSid());
            }
        });
        userRepository.deleteById(Long.parseLong(id));
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/users/{id}/scores", method= RequestMethod.POST )
    @PreAuthorize("#id.equals(#p.IDasString)")
    public ResponseEntity<?> addScore(@PathVariable("id") String id, @RequestBody long score, @AuthenticationPrincipal TDUserDetails p){
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if(user.isPresent()) {
            scoreRepository.save(new Score(score, Instant.now(), user.get()));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/users/{id}/scores", method= RequestMethod.GET )
    public ResponseEntity<?> getScores(@PathVariable("id") String id){
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if(user.isPresent()) {
            List<PublicScore> scores = scoreRepository.findAllByUser(user.get()).stream().map(PublicScore::new).collect(Collectors.toList());
            return ResponseEntity.ok(scores);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/users/**/scores/{sid}", method= RequestMethod.GET )
    public ResponseEntity<?> getScore(@PathVariable("sid") String sid){
        Optional<Score> s = scoreRepository.findById(Long.parseLong(sid));
        if(s.isPresent()){
            PublicScore score = new PublicScore(s.get());
            return ResponseEntity.ok(score);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/users/{id}/scores/{sid}", method= RequestMethod.DELETE )
    @PreAuthorize("#id.equals(#p.IDasString)")
    public ResponseEntity<?> deleteScore(@PathVariable("sid") String sid, @PathVariable("id") String id, @AuthenticationPrincipal TDUserDetails p){
        if(scoreRepository.findById(Long.parseLong(sid)).isPresent() && scoreRepository.findById(Long.parseLong(sid)).get().getUser().getId() == Long.parseLong(id) ) {
            scoreRepository.deleteById(Long.parseLong(sid));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/scores", method= RequestMethod.GET )
    public ResponseEntity<?> getTopScores(@RequestParam(value = "limit", required = false, defaultValue = "-1") String limit){
        List<PublicScore> scores = scoreRepository.findAll().stream().map(PublicScore::new).sorted().collect(Collectors.toList());
        try {
            int l = Integer.parseInt(limit);
            if(l == -1) {
                return ResponseEntity.ok(scores);
            } if(l > scores.size() - 1) {
                return ResponseEntity.ok(scores);
            } else {
                return ResponseEntity.ok(scores.subList(0, l));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
