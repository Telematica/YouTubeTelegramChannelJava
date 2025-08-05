#!/bin/zsh

# Reference
# https://litestream.io/alternatives/cron/
# https://gist.github.com/zeroc0d3/b74a35ce52a0f5225c906adce2cf09e5


#❗️Do not use `cp` to back up SQLite databases. It is not transactionally safe.

# Ensure script stops when commands fail.
# set -e

SQLITE_DB_PATH=$HOME/YouTubeTelegramChannelJava/src/main/resources/database/sqlite/db.sqlite;
SQLITE_DB_BACKUP_PATH=$HOME/YouTubeTelegramChannelJava/src/main/resources/database/sqlite/db.backup.sqlite;

function backup() {
    TYPE=$(echo $1)
    if [ $TYPE = "plain" ]
        then
            sqlite3 $SQLITE_DB_PATH < $HOME/YouTubeTelegramChannelJava/src/main/resources/database/clean-log.sql
            sqlite3 $SQLITE_DB_PATH ".backup '$SQLITE_DB_BACKUP_PATH'"
    elif [ $TYPE = "vacuum" ]
        then
            sqlite3 $SQLITE_DB_PATH < $HOME/YouTubeTelegramChannelJava/src/main/resources/database/sql/clean-log.sql
            rm $SQLITE_DB_BACKUP_PATH
            sqlite3 $SQLITE_DB_PATH "VACUUM INTO '$SQLITE_DB_BACKUP_PATH'"
    else
        echo "No valid type entered."
        return 1
    fi

    return 0
}

backup "vacuum" && cd $HOME/YouTubeTelegramChannelJava/ && git add src/main/resources/database/sqlite/db.backup.sqlite && git commit -m "chore(): DB Backup." && git push origin main
