#!/bin/zsh
export ENV_VAR_FILE_PATH=$HOME/YouTubeTelegramChannelJava/scripts/envvars.sh

function writepidtoenvfile {
    sed -i.bak -E "s/PID=[0-9]+/PID=$PID/" $ENV_VAR_FILE_PATH
}

function runjar {
    java \
    -Xms256m -Xmx512m \
    -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=2 \
    -XX:ConcGCThreads=1 -XX:+UseCompressedOops -XX:+UseStringDeduplication -XX:+OptimizeStringConcat \
    -XX:+UseCompressedClassPointers -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:+UseAdaptiveSizePolicy \
    -XX:G1HeapRegionSize=16m \
    -jar $HOME/YouTubeTelegramChannelJava/target/YouTubeTelegramChannelJava-1.0-SNAPSHOT-jar-with-dependencies.jar \
    --quiet --multi-thread & PID=$!
}

if [ ! -f $ENV_VAR_FILE_PATH ]; then
    echo "<envvars.sh> file not found! Please create it."
    return 1;
fi

source $ENV_VAR_FILE_PATH

if ps -p $PID > /dev/null
    then
        echo "$PID is running, skipping."
        return 1;
    else
        runjar
        writepidtoenvfile
        echo "PID created: $PID, starting new JAR app instance."
        return 0;
fi
