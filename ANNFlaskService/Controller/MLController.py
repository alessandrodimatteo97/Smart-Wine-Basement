from flask import Blueprint, request, make_response, Response
import joblib as jl

main = Blueprint('main', __name__)

columns = ['Fixed Acidity',
           'Volatile Acidity',
           'Citric Acid',
           'Residual Sugar',
           'Chlorides',
           'Free Sulfur Dioxide',
           'Total Sulfur Dioxide',
           'Density',
           'Ph',
           'Sulphates',
           'Alcohol']

red_classifier = jl.load('./MLModels/redclf.joblib')
white_classifier = jl.load('./MLModels/whiteclf.joblib')


@main.route('/', methods=['GET'])
def main_get():
    return "ciao"


@main.route('/predict/wine', methods=['POST'])
def main_white_wine():
    wine_type = request.values.get('wine')
    values = []

    for col in columns:
        value = request.values.get(col)
        print(value)
        if value is None:
            return 'Error'
        values.append(value)

    if wine_type == 'red':
        y_pred = red_classifier.predict([values])[0]
    elif wine_type == 'white':
        y_pred = white_classifier.predict([values])[0]
    else:

        return 'Error you need to specify the type of wine'

    if y_pred == 0:
        return 'not so good'
    elif y_pred == 1:
        return 'good'
    else:
        return 'excellent'
