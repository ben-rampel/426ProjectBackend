let twos = 0;
let fours = 0;
for(let i = 0; i < 5000; i++) {
	let rand = Math.floor(Math.random() * 10); 
	rand < 9 ? twos++ : fours++; 
}
console.log("percent two tiles: " + twos/5000);
