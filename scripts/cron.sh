#!/bin/zsh
export PID=9935

if ps -p $PID > /dev/null
    then
        echo "$PID is running, skipping."
        return 1;
    else
        source $HOME/YouTubeTelegramChannelJava/scripts/envvars.sh && java \
        -Xms256m -Xmx512m \
        -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=2 \
        -XX:ConcGCThreads=1 -XX:+UseCompressedOops -XX:+UseStringDeduplication -XX:+OptimizeStringConcat \
        -XX:+UseCompressedClassPointers -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:+UseAdaptiveSizePolicy \
        -XX:G1HeapRegionSize=16m \
        -jar $HOME/YouTubeTelegramChannelJava/target/YouTubeTelegramChannelJava-1.0-SNAPSHOT-jar-with-dependencies.jar \
        --quiet --multi-thread & PID=$!
        sed -i.bak -E "s/PID=[0-9]+/PID=$PID/" $HOME/YouTubeTelegramChannelJava/scripts/cron.sh
        return 0;
fi
