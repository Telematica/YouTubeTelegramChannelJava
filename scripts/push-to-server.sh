#!/bin/zsh

REPO_DIR=$HOME/YouTubeTelegramChannelJava
REMOTE_DIR=/Users/telematica/YouTubeTelegramChannelJava/target

scp \
$REPO_DIR/target/YouTubeTelegramChannelJava-1.0-SNAPSHOT-jar-with-dependencies.jar \
telematica@192.168.68.122:$REMOTE_DIR/YouTubeTelegramChannelJava-1.0-SNAPSHOT-jar-with-dependencies.jar