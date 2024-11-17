# Rest Meals curl requests

### GetAll
`curl localhost:8080/topjava/rest/meals`
### GetById
`curl localhost:8080/topjava/rest/meals/100003`
### DeleteById
`curl -X DELETE localhost:8080/topjava/rest/meals/100003`
### Create
`curl -X POST localhost:8080/topjava/rest/meals -H "Content-Type: application/json" -d '{"dateTime":"2020-02-01T18:00:00","description":"Созданный ужин","calories":300}'`
### Update
`curl -X PUT localhost:8080/topjava/rest/meals/100003 -H "Content-Type: application/json" -d '{"id":100003,"dateTime":"2020-01-30T10:02:00","description":"Обновленный завтрак","calories":200}'`
### GetBetween
`curl "localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=07:00&endDate=2020-01-31&endTime=11:00"`

# Admin/Profile curl requests
### Admin userWithMeals
`curl localhost:8080/topjava/rest/admin/users/with-meals/100001`
### Profile userWithMeals
`curl localhost:8080/topjava/rest/profile/with-meals`
