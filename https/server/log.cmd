curl -i -X POST -d username=user -d password=user http://localhost:8080/login -c cookies.txt
curl -i  --header "Accept:application/json" -X GET -b cookies.txt GET http://localhost:8080/api/noleggi


rem curl --cacert ca.pem -k -i -d username=user -d password=user POST -H 'Content-Type: application/json' "https://localhost:8443/spring-security-rest/login"