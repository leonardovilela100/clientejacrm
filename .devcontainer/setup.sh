#!/bin/bash

echo "[SETUP] Aguardando o banco de dados iniciar..."

# Aguarda até o PostgreSQL estar aceitando conexões (máx 30s)
for i in {1..30}; do
  if PGPASSWORD=senha123 psql -h db -U postgres -c '\l' >/dev/null 2>&1; then
    echo "[SETUP] PostgreSQL está pronto!"
    break
  fi
  echo "[SETUP] Aguardando... ($i/30)"
  sleep 1
done

# Verifica se o banco 'clientejacrm' já existe
DB_EXISTS=$(PGPASSWORD=senha123 psql -h db -U postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'clientejacrm'" | grep -q 1 && echo "sim" || echo "nao")

if [ "$DB_EXISTS" = "nao" ]; then
  echo "[SETUP] Banco 'clientejacrm' não encontrado. Criando..."
  PGPASSWORD=senha123 psql -h db -U postgres -c "CREATE DATABASE clientejacrm;"
else
  echo "[SETUP] Banco 'clientejacrm' já existe. Nenhuma ação necessária."
fi
