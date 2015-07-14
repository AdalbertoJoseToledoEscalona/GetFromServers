CREATE TABLE get_from_servers.servidores (
       id_servidor VARCHAR(5) NOT NULL
     , nombre VARCHAR(100) NOT NULL
     , puerto INTEGER
     , protocolo VARCHAR(10)
     , orden INTEGER
     , PRIMARY KEY (id_servidor)
);

CREATE TABLE get_from_servers.agendas (
       id_agenda VARCHAR(5) NOT NULL
     , minuto INTEGER
     , hora INTEGER
     , dia INTEGER
     , mes INTEGER
     , dia_semana INTEGER
     , PRIMARY KEY (id_agenda)
);

CREATE TABLE get_from_servers.hilos (
       id_hilo VARCHAR(5) NOT NULL
     , nombre VARCHAR(100) NOT NULL
     , auto_reproducir BOOLEAN NOT NULL
     , PRIMARY KEY (id_hilo)
);

CREATE TABLE get_from_servers.hilos_x_agendas (
       id_hilo_x_agenda VARCHAR(5) NOT NULL
     , id_hilo VARCHAR(5) NOT NULL
     , id_agenda VARCHAR(5) NOT NULL
     , PRIMARY KEY (id_hilo_x_agenda)
     , INDEX (id_hilo)
     , CONSTRAINT FK_hilos_x_agendas_1 FOREIGN KEY (id_hilo)
                  REFERENCES get_from_servers.hilos (id_hilo)
     , INDEX (id_agenda)
     , CONSTRAINT FK_hilos_x_agendas_2 FOREIGN KEY (id_agenda)
                  REFERENCES get_from_servers.agendas (id_agenda)
);

CREATE TABLE get_from_servers.archivos_buscar (
       id_archivo VARCHAR(5) NOT NULL
     , nombre_original VARCHAR(100) NOT NULL
     , ruta_original VARCHAR(100) NOT NULL
     , nombre_final VARCHAR(100)
     , ruta_final VARCHAR(100)
     , id_servidor_original VARCHAR(5) NOT NULL
     , id_servidor VARCHAR(5) NOT NULL
     , PRIMARY KEY (id_archivo)
     , INDEX (id_servidor_original)
     , CONSTRAINT FK_archivos_buscar_1 FOREIGN KEY (id_servidor_original)
                  REFERENCES get_from_servers.servidores (id_servidor)
     , INDEX (id_servidor)
     , CONSTRAINT FK_archivos_buscar_2 FOREIGN KEY (id_servidor)
                  REFERENCES get_from_servers.servidores (id_servidor)
);

CREATE TABLE get_from_servers.archivos_x_hilos (
       id_archivo_x_hilo VARCHAR(5) NOT NULL
     , id_hilo VARCHAR(5) NOT NULL
     , id_archivo VARCHAR(5) NOT NULL
     , orden INTEGER NOT NULL
     , PRIMARY KEY (id_archivo_x_hilo)
     , INDEX (id_hilo)
     , CONSTRAINT FK_archivos_x_hilos_1 FOREIGN KEY (id_hilo)
                  REFERENCES get_from_servers.hilos (id_hilo)
     , INDEX (id_archivo)
     , CONSTRAINT FK_archivos_x_hilos_2 FOREIGN KEY (id_archivo)
                  REFERENCES get_from_servers.archivos_buscar (id_archivo)
);

CREATE TABLE get_from_servers.logs (
       fecha_hora DATETIME NOT NULL
     , descripcion TEXT
     , id_hilo VARCHAR(5) NOT NULL
     , id_archivo VARCHAR(5)
     , id_servidor VARCHAR(5)
     , id_agenda VARCHAR(5)
     , id_archivo_x_hilo VARCHAR(5)
     , id_hilo_x_agenda VARCHAR(5)
     , PRIMARY KEY (fecha_hora)
     , INDEX (id_hilo)
     , CONSTRAINT FK_logs_1 FOREIGN KEY (id_hilo)
                  REFERENCES get_from_servers.hilos (id_hilo)
     , INDEX (id_archivo)
     , CONSTRAINT FK_logs_2 FOREIGN KEY (id_archivo)
                  REFERENCES get_from_servers.archivos_buscar (id_archivo)
     , INDEX (id_servidor)
     , CONSTRAINT FK_logs_3 FOREIGN KEY (id_servidor)
                  REFERENCES get_from_servers.servidores (id_servidor)
     , INDEX (id_agenda)
     , CONSTRAINT FK_logs_4 FOREIGN KEY (id_agenda)
                  REFERENCES get_from_servers.agendas (id_agenda)
     , INDEX (id_archivo_x_hilo)
     , CONSTRAINT FK_logs_5 FOREIGN KEY (id_archivo_x_hilo)
                  REFERENCES get_from_servers.archivos_x_hilos (id_archivo_x_hilo)
     , INDEX (id_hilo_x_agenda)
     , CONSTRAINT FK_logs_6 FOREIGN KEY (id_hilo_x_agenda)
                  REFERENCES get_from_servers.hilos_x_agendas (id_hilo_x_agenda)
);

