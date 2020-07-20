# Blog

The technology stack used in this project is Spring Boot, Thymeleaf, Spring Data JPA, MySQL. I have developed and expand the original project especially in the part of admin system. In admin part, JWT Authorization was employed to replace Session and to make the App stateless, but the problem is we need to send every request with token. Fortunately, it can be solved by using Ajax to add the Authorization header for every request. To achieve this, I have rewritten the whole admin system using JQuery Ajax. The admin system is therefore similar to a single page application and thanks to the JWT the project now is stateless.   

## Project Demo
The reason why I made this project stateless is that I want to try Cloud Run. Stateless is a requirement of using Cloud Run. The following is a link of my blog website in Cloud Run which can serve as a demo of this project.
<https://chen-blog-soep4ktona-ez.a.run.app>