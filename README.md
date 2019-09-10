# pes-laresistencia-server

## Database

Download docker image first (just once)

`docker pull postgres`

Start postgres

`docker run --name postgres-pes -e POSTGRES_DB=pes -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres`

## TO Update the server

connect via ssh

`ssh pes@159.65.117.25` o `ssh pes@159.65.117.25 -i .ssh/pes` 

bash

cd server

./gradlew build -x test

docker-compose build

docker-compose up -d

docker-compose logs