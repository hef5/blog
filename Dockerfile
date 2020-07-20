FROM adoptopenjdk/openjdk11:alpine-jre

# Copy local code to the container image.
WORKDIR /app

# Copy the jar to the production image from the builder stage.
# path in container: ./blog-key.json
COPY ./blog-key.json /blog-key.json

# path in container: ./blog.jar
COPY target/blog-0.0.1-SNAPSHOT.jar /blog.jar

ENV GOOGLE_APPLICATION_CREDENTIALS="/blog-key.json"
ENV PORT=8080

# java -jar /app/blog.jar
ENTRYPOINT ["java","-Dspring.profiles.active=pro", "-jar","/blog.jar"]
