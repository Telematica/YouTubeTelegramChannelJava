#!/bin/zsh
export PID=76249

if ps -p $PID > /dev/null
    then
        echo "$PID is running, skipping."
        return 1;
    else
        java -jar $HOME/YouTubeTelegramChannelJava/target/YouTubeTelegramChannelJava-1.0-SNAPSHOT-jar-with-dependencies.jar & PID=$!
        sed -i.bak -E "s/=[0-9]+/=$PID/" $HOME/YouTubeTelegramChannelJava/scripts/cron.sh
        return 0;
fi
