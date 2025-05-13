import requests
import json

base_url = "http://localhost:4567/todos"

def print_json(label, data):
    print(f"\n🔹 {label}")
    print(json.dumps(data, indent=2, ensure_ascii=False))

# 1. Créer un todo valide
todo_valide = {"name": "Lire Clean Code"}
params = {"dueDate": "2025-05-15"}

try:
    response = requests.post(base_url, json=todo_valide, params=params)
    print_json("Todo valide créé", response.json())
except Exception as e:
    print(f"Erreur POST todo valide : {e}")

# 2. Créer un todo avec nom trop long
nom_trop_long = {"name": "x" * 70}

try:
    response = requests.post(base_url, json=nom_trop_long)
    print(f"\n🔹 Tentative avec nom trop long - Status: {response.status_code}")
    print(response.text)
except Exception as e:
    print(f"Erreur POST nom trop long : {e}")

# 3. Créer un todo en doublon
try:
    response = requests.post(base_url, json=todo_valide)
    print(f"\n🔹 Tentative doublon - Status: {response.status_code}")
    print(response.text)
except Exception as e:
    print(f"Erreur POST doublon : {e}")

# 4. Récupérer tous les todos
try:
    response = requests.get(base_url)
    print_json("Liste de tous les todos", response.json())
except Exception as e:
    print(f"Erreur GET todos : {e}")
