## To build
mvn clean compile
mvn clean install -Dgib.enabled=false

## To run
mvn spring-boot:run -Dgib.enabled=false > running.output.txt 2>&1 &

## Spring 5 Reactive Project

### The Course
The "REST With Spring" Classes: http://bit.ly/restwithspring

### Relevant Articles

- [Introduction to the Functional Web Framework in Spring 5](http://www.baeldung.com/spring-5-functional-web)
- [Spring 5 WebClient](http://www.baeldung.com/spring-5-webclient)
- [Exploring the Spring 5 MVC URL Matching Improvements](http://www.baeldung.com/spring-5-mvc-url-matching)
- [Reactive WebSockets with Spring 5](http://www.baeldung.com/spring-5-reactive-websockets)
- [Spring Webflux Filters](http://www.baeldung.com/spring-webflux-filters)
- [How to Set a Header on a Response with Spring 5](http://www.baeldung.com/spring-response-header)
- [Spring Webflux and CORS](http://www.baeldung.com/spring-webflux-cors)
- [Handling Errors in Spring WebFlux](http://www.baeldung.com/spring-webflux-errors)
- [Server-Sent Events in Spring](https://www.baeldung.com/spring-server-sent-events)
- [A Guide to Spring Session Reactive Support: WebSession](https://www.baeldung.com/a-guide-to-spring-session-reactive-support-websession/)

## To install and setup redis on mac
https://1upnote.me/post/2018/06/install-config-redis-on-mac-homebrew/
brew update
brew upgrade
brew install redis
brew services start redis
brew services stop redis
If you don’t want/need a background service you can just run: redis-server
vim /usr/local/etc/redis.conf
And start Redis with the configuration file as: redis-server /usr/local/etc/redis.conf
Test if Redis is running: redis-cli ping

