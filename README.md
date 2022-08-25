# spring-test-app

## Deployment

1. Создать сеть докера
```
docker network create -d bridge  sprin-test-network
```

2. Выполнить в [каталоге](infrastructure/spring-test-application) **docker compose up -d**:
- консоль keycloak [https://localhost:4443](https://localhost:4443)
- config-server: https://localhost:4071/{APPLICATION_NAME}/{PROFILE}, где APPLICATION_NAME - имя сервиса
         PROFILE - профиль (default по умолчанию)
- eureka дашборд [https://localhost:4070](https://localhost:4070)
- application [https://localhost:4080](https://localhost:4070)
- postgres port: **4432**

3. Для сборки логов используется vector.dev и fluentd драйвер


## Полезности:
### Keycloak
1. [authorization code](https://localhost:4443/realms/spring-test-app/protocol/openid-connect/auth?response_type=code&client_id=sprint-test-app-client&redirect_uri=http://localhost:9874)
```
curl --location --request POST 'https://localhost:4443/realms/spring-test-app/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'code=f54be45d-1c3e-4787-9d93-de285bfc57c8.fd4d1f39-a1fe-41a6-a72c-e686c0b93457.ca18a3c1-7ea2-4df9-b17d-06226d93ccc0' \
--data-urlencode 'client_id=sprint-test-app-client' \
--data-urlencode 'client_secret=Ege31HrZQAGSHSwgitf2SOZhvwvYCNEQ' \
--data-urlencode 'redirect_uri=http://localhost:9874' \
--data-urlencode 'grant_type=authorization_code'
```

## Config bus:
https://medium.com/swlh/spring-cloud-config-bus-auto-refresh-properties-for-clients-d18fa4c036cb


