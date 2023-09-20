#Mediscreen Builder

echo Started building Mediscreen project –

echo Building jar files...

(cd ./ui || exit; ng build --prod --aot --build-optimizer --optimization --output-hashing=all)
(cd ./gateway || exit; mvn clean install -Dmaven.test.skip=true)
(cd ./microservice-patients || exit; mvn clean install -Dmaven.test.skip=true)
(cd ./microservice-history || exit; mvn clean install -Dmaven.test.skip=true)
(cd ./microservice-assessment || exit; mvn clean install -Dmaven.test.skip=true)

rm -R ./ui_server/src/main/resources/static
mkdir ./ui_server/src/main/resources/static
cp ./ui/dist/ui/* ./ui_server/src/main/resources/static

(cd ./ui_server || exit; mvn clean install -Dmaven.test.skip=true)

echo Overwriting jar files in the docker folder...

cp -f ./ui_server/target/ui.jar ./docker/mediscreen/ui
cp -f ./gateway/target/gateway.jar ./docker/mediscreen/gateway
cp -f ./microservice-patients/target/mpatients.jar ./docker/mediscreen/patients
cp -f ./microservice-history/target/mhistory.jar ./docker/mediscreen/history
cp -f ./microservice-assessment/target/massessment.jar ./docker/mediscreen/assessment

echo Finished building Mediscreen project.
