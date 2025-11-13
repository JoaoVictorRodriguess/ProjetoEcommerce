# Usa uma imagem base leve do Python/api/produtos/todos
FROM python:3.12-slim

# Define o diretório de trabalho
WORKDIR /home/brechot/einstein/api/djangoEcommerce

# Instala dependências do sistema necessárias pro mysqlclient
RUN apt-get update && apt-get install -y \
    gcc \
    default-libmysqlclient-dev \
    pkg-config \
    && rm -rf /var/lib/apt/lists/*

# Copia o requirements se tiver, senão instala direto
RUN pip install --no-cache-dir django mysqlclient djangorestframework

# Copia o projeto Django pro container
COPY . .

# Expõe a porta padrão do Django
EXPOSE 8000

# Comando para rodar o servidor de desenvolvimento
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
