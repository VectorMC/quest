package net.avicus.quest.database;

import lombok.Getter;

public class DatabaseConfig {
    @Getter private final String host;
    @Getter private final String database;
    @Getter private final String username;
    @Getter private final String password;
    @Getter private final boolean reconnectEnabled;

    private DatabaseConfig(String host, String database, String username, String password, boolean reconnectEnabled) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.reconnectEnabled = reconnectEnabled;
    }

    public String getUrl() {
        StringBuilder url = new StringBuilder();
        url.append(String.format("jdbc:mysql://%s/%s", this.host, this.database));
        url.append("?");

        if (this.reconnectEnabled)
            url.append("autoReconnect=true");

        return url.toString();
    }

    public static class Builder {
        private String host;
        private String database;
        private String username;
        private String password;
        private boolean reconnectEnabled;

        public Builder(String host, String database, String username, String password) {
            this.host = host;
            this.database = database;
            this.username = username;
            this.password = password;
        }

        public DatabaseConfig build() {
            return new DatabaseConfig(this.host, this.database, this.username, this.password, this.reconnectEnabled);
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder database(String database) {
            this.database = database;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder reconnect(boolean reconnectEnabled) {
            this.reconnectEnabled = reconnectEnabled;
            return this;
        }
    }
}
