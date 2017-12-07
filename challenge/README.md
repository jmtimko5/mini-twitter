## Mini-Twitter
This project is a small RESTful backend mimicking the behavior of Twitter.  All project URLs are only accessible through HTTPS.  When run locally the REST API is accessible at ```https://localhost:8080/api/v1```.  The only current database supported is an in memory H2 database.  Basic authentication is supported for API requests.  Note: In order to allow for easy test and use all user passwords are currently set to "abc123".   

### API Endpoints
#### Messages
* ```/messages```
  * GET: Get all messages for your user as specified in HTTPS Authorization Headers.
| Parameter | Type   | Purpose                                | Required? |
   |-----------|--------|----------------------------------------|-----------|
   | search    | string | Provide list of comma separated strings for inclusive keyword search            | no       |

* ```/network/following```
  * GET: Get the list of users your user is following.

* ```/network/followers```
  * GET: Get the list of users your user followers.

* ```/network/follow```
  * POST: Follow another user. Neither of the below parameters is required but you must provide at least one.  If successful return the user you followed.
| Parameter | Type   | Purpose                                | Required? |
|-----------|--------|----------------------------------------|-----------|
| id    | Integer | ID of user to follow            | no       |
| handle    | String | User handle of user to follow            | no       |

* ```/network/unfollow```
  * POST: Unfollow another user. Neither of the below parameters is required but you must provide at least one.  If successful returns the user you unfollowed.
| Parameter | Type   | Purpose                                | Required? |
|-----------|--------|----------------------------------------|-----------|
| id    | Integer | ID of user to unfollow            | no       |
| handle    | String | User handle of user to unfollow            | no       |

* ```/network/shortestpath/{handleToSearch}```
  * GET: Get the length of the shortest follow graph path between your user and the user with the handle ```handleToSearch``` via the users you follow.  Return the length of the path or ```-1``` if not path exists.



### Future Work
* Pagination of request responses
* Automated unit and integration tests
* Better exception handling
* HTTP -> HTTPS redirection
* More securely storing user passwords
* HATEOAS support
* Database configurability
