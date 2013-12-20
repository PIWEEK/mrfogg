![MR FOGG](https://raw.github.com/PIWEEK/mrfogg/master/mrfogg-front/app/images/mrfogg-logo.png)

# MR FOGG
Collaborative travel planning

## Test commands

The test commands use `curl`

To populate the database, we use a custom `fixtures` endpoint. The endpoint loads static data, so it should be used one time only (if not, it will fail due database constraints).

```bash
curl -X GET -H "Content-Type: application/json" http://localhost:8080/fixtures/populate
```

Obtain the user list

```bash
curl -X GET -H "Content-Type: application/json" http://localhost:8080/users/
```

Login to the application

```bash
curl -X POST -H "Content-Type: application/json" -d '{ "email": "my@user.com", "password": "email" }' http://localhost:8080/auth/login
```

Current user

```bash
curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer myToken" http://localhost:8080/users/me
```

User by id

```bash
curl -X GET -H "Content-Type: application/json" -H "Authorization: Bearer myToken" http://localhost:8080/users/1
```
