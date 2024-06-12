FROM nginx:latest

ENV VERSION="1.0.0-SNAPSHOT"

ENV API_URL http://localhost:8080/api


ADD dist/untitled1 /usr/share/nginx/html/
ADD docker/etc/nginx/templates/ /etc/nginx/templates/

EXPOSE 80
