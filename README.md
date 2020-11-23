# 426ProjectBackend
Java Spring based API backend for COMP 426 final project Tower Defense game. 
Includes JWT authentication and authorization, user and score storage in an ~~embedded H2 database~~ Heroku Postgresql, with API endpoints for user information and top scores.

# API Description

## /users/  \[Public\]

GET: View all registered users: id, username, avatar url (not implemented), and an array of their scores on the tower defense game


## /scores/  \[Public\]

GET: View all all users' scores sorted by score descending. Can provide URL parameter "limit" to limit to a certain number of scores.


## /register \[Public\]

POST: Register a user account. Form data must include username and password


## /authenticate \[Public\]

POST: Provide username and password in form data--Endpoint responds with a JWT token to authenticate as this user for subsequent API calls


## /users/{id} \[Must be logged in\]

GET: View info about a specific user based on id


PUT: Update a user's information (requires token from this account)


DELETE: Delete a user account (requires token from this account)


## /users/{id}/scores  \[Must be logged in\]

GET: Array of all this user's scores


POST: Add a new score (requires token from this account)


## /users/{id}/scores/{id}  \[Must be logged in\]

GET: View particular score


DELETE: Delete this score (requires token from this account)


