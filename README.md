
## Open API page

Visit this for overview of Rest API
```
http://localhost:9180/swagger-ui/index.html
```

This will show a very basic but useable frontend to upload WAV files and convert them into Rhubarb's lipsync json as a result. 

![webinterface.png](images%2Fwebinterface.png)

## Docker image

Create a docker with this command :

```
export JAVA_HOME=~/Library/Java/JavaVirtualMachines/temurin-17.0.1/Contents/Home 
./gradlew clean build
docker build --no-cache -t  franzzle/lipsync-flux-api .
```

## Docker compose run

```
docker-compose up lipsync
```
