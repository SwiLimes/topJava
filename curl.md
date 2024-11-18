# Meals API

### Get all meals 
`curl localhost:8080/topjava/rest/meals`
### Get meal by id
`curl localhost:8080/topjava/rest/meals/100003`
### Delete meal by id
`curl -X DELETE localhost:8080/topjava/rest/meals/100003`
### Create meal
`curl -X POST localhost:8080/topjava/rest/meals -H "Content-Type: application/json" -d '{"dateTime":"2020-02-01T18:00:00","description":"Созданный ужин","calories":300}'`
### Update meal
`curl -X PUT localhost:8080/topjava/rest/meals/100003 -H "Content-Type: application/json" -d '{"id":100003,"dateTime":"2020-01-30T10:02:00","description":"Обновленный завтрак","calories":200}'`
### Get meals with filter between dates/times
`curl "localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=07:00&endDate=2020-01-31&endTime=11:00"`

# Admin/Profile API
### Get user with meals by user id
`curl localhost:8080/topjava/rest/admin/users/100001/with-meals`
### Get user with meals
`curl localhost:8080/topjava/rest/profile/with-meals`
