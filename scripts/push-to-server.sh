#!/bin/zsh

printf "Enter IP Address: " && read -r IP_ADDRESS
printf "Enter username: " && read -r USER

REPO_DIR=$HOME/YouTubeTelegramChannelJava
REMOTE_DIR=/Users/$USER/YouTubeTelegramChannelJava/target
JAR_FILE=YouTubeTelegramChannelJava-1.0-SNAPSHOT-jar-with-dependencies.jar

scp $REPO_DIR/target/$JAR_FILE $USER@$IP_ADDRESS:$REMOTE_DIR/$JAR_FILE && \
echo "Successfully pushed to server at $IP_ADDRESS."