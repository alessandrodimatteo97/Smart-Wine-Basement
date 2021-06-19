from flask import Flask
from Controller.MLController import main

app = Flask(__name__)
app.register_blueprint(main)





