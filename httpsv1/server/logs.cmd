
curl -k -X POST https://localhost:8443/login  -u user:user -c cookies.txt 
curl -k -i  --header "Accept:application/json" -X GET  GET https://localhost:8443/api/noleggi
echo off
rem curl --cacert server.pem -X POST https://localhost:8443/login  -d username=user   -d password=user -c cookies.txt 

rem curl -X POST https://localhost:8443/login  -d username=user   -d password=user -c cookies.txt -H 'Content-Type: application/x-www-form-urlencoded'

rem -H 'Content-Type: application/x-www-form-urlencoded'

rem curl -k -i -X POST  https://localhost:8443/login -d username=user -d password=user -c cookies.txt
rem curl -i  --header "Accept:application/json" -X GET -b cookies.txt GET https://localhost:8443/api/noleggi


rem curl --cacert ca.pem -k -i -d username=user -d password=user POST -H 'Content-Type: application/json' "https://localhost:8443/spring-security-rest/login"