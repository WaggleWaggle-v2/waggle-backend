FROM openjdk:17-slim
RUN apt-get update && apt-get install -y curl
COPY ./application_config.jar application_config
EXPOSE 9000
CMD ["java", "-jar", "/application_config"]