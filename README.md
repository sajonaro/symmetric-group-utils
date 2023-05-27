# symmetric-group-utils
functions helping explore problems related to symmetric group Sn  (https://en.wikipedia.org/wiki/Symmetric_group)


<br/>
<br/>

### how to use this project?

#### as a library
via deps.edn
```
{:deps
 {io.github.sajonaro/symmetric-group-utils {:git/tag "v0.0.1" :git/sha "cb6762a"}}}
``` 
<br/>

#### as a stanalone cli application
- install GraalVM (to be able to generate native app)
- clone the repository, update path to GraalVM in build-cli_local.sh
- execute build-cli_local.sh script to build cli application:
```
sudo chmod +x build-cli_local.sh
./build-cli_local.sh

```
- You can now use cli application like so:
```
./gu -?

```
<br/>

#### as a docker container 
- prerequisites: docker installed
- run: sudo docker_build.sh
- then: sudo docker_run.sh

<br/>
