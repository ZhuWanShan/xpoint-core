## Download XPoint

Git Clone xpoint-core.git

## Download script
Git Clone xpoint-script.git


## Change Configuration
xpoint-core/bin
Change "xpoint_script_root_dir" in conf.js


## Proxy
ssh -L 8084:bi-endpoint-01-mp:48080 mb@bi-endpoint-01-mp


## Run 

cd xpoint-core
java -jar -Dxpoint.conf=bin/conf.js bin/api-server-1.0-SNAPSHOT-all.jar -http.port=':9999'

