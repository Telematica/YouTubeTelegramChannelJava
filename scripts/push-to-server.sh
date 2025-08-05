#!/bin/zsh

REPO_DIR=$HOME/YouTubeTelegramChannelJava
REMOTE_DIR=/Users/telematica/YouTubeTelegramChannelJava/target

echo "Enter IP Address: " && read IP_ADDRESS && \
scp \
$REPO_DIR/target/YouTubeTelegramChannelJava-1.0-SNAPSHOT-jar-with-dependencies.jar \
telematica@$IP_ADDRESS:$REMOTE_DIR/YouTubeTelegramChannelJava-1.0-SNAPSHOT-jar-with-dependencies.jar && \
echo "Successfully pushed to server at $IP_ADDRESS"