from typing import Any, Dict
from locust import HttpUser, between, constant_throughput, task
import random
car_request = {
    "gamification": False,
    "routes": [
        {
            "steps": [
                {
                    "vehicle": "/car",
                    "distance": 3000.0,
                }
            ]
        }
    ]
}

bike_request = {
    "gamification": False,
    "routes": [
        {
            "steps": [
                {
                    "vehicle": "/car",
                    "distance": 3000.0,
                },
                {
                    "vehicle": "/bike/e-bike",
                    "distance": 12000.0,
                }
            ]
        }
    ]
}

metro_request = {
    "gamification": False,
    "routes": [
        {
            "steps": [
                {
                    "vehicle": "/car",
                    "distance": 3000.0,
                },
                {
                    "vehicle": "/bike/e-bike",
                    "distance": 12000.0,
                },
                {
                    "vehicle": "/metro",
                    "distance": 6000.0,
                }
            ]
        }
    ]
}

uni_request = {
    "gamification": False,
    "routes": [
        {
            "steps": [
                {
                    "vehicle": "/bus",
                    "line": "169",
                    "start": "Müggelheim/Dorf (Berlin)",
                    "end": "S Köpenick (Berlin)",
                },
                {
                    "vehicle": "/train",
                    "line": "S3",
                    "start": "S Köpenick (Berlin)",
                    "end": "S Tiergarten (Berlin)",
                }
            ]
        }
    ]
}

random.seed(None)


class EcoTrekkerUser(HttpUser):

    vehicles = [
        "/car", "/car/e-car", "/car/ice-car",
        "/bike", "/bike/e-bike", "/bike/bicycle",
        "/tram", "/metro", "/train",
        "/bus", "/bus/e-bus", "/bus/ice-bus",
    ]

    step_pairs = {
        "/tram": [
            ("60", "Bruno-Wille-Str. (Berlin)", "Alte Försterei (Berlin)"),
            ("60", "Haeckelstr. (Berlin)", "Gelnitzstr. (Berlin)"),
            ("61", "S Schöneweide/Sterndamm (Berlin)",
             "Wassersportzentrum (Berlin)"),
            ("27", "Pablo-Neruda-Str. (Berlin)",
             "Falkenberger Str./Berliner Allee (Berlin)")
        ],
        "/metro": [
            ("U8", "U Leinestr. (Berlin)", "S+U Wittenau (Berlin)"),
            ("U7", "U Lipschitzallee (Berlin)", "U Fehrbelliner Platz (Berlin)"),
            ("U9", "U Leopoldplatz (Berlin)", "U Friedrich-Wilhelm-Platz (Berlin)"),
            ("U2", "U Gleisdreieck (Berlin)",
             "U Olympia-Stadion (Berlin)")
        ],
        "/train": [
            ("S3", "S Köpenick (Berlin)", "S Tiergarten (Berlin)"),
            ("S8", "S Wildau", "S+U Pankow (Berlin)"),
            ("S42", "S Westend (Berlin)", "S Landsberger Allee (Berlin)"),
            ("S1", "S Borgsdorf", "S+U Yorckstr. (Großgörschenstr.) (Berlin)")
        ],
        "/bus": [
            ("169", "Semliner Straße (Berlin)", "Müggelheim/Dorf (Berlin)"),
            ("194", "Elsenstr./Kiefholzstr. (Berlin)", "S Ostkreuz Bhf (Berlin)"),
            ("M43", "S Treptower Park (Berlin)",
             "Columbiadamm/Platz der Luftbrücke (Berlin)"),
            ("N96", "Schleusinger Str. (Berlin)", "S+U Wuhletal (Berlin)")
        ],
        "/bus/e-bus": [
            ("169", "Semliner Straße (Berlin)", "Müggelheim/Dorf (Berlin)"),
            ("194", "Elsenstr./Kiefholzstr. (Berlin)", "S Ostkreuz Bhf (Berlin)"),
            ("M43", "S Treptower Park (Berlin)",
             "Columbiadamm/Platz der Luftbrücke (Berlin)"),
            ("N96", "Schleusinger Str. (Berlin)", "S+U Wuhletal (Berlin)")
        ],
        "/bus/ice-bus": [
            ("169", "Semliner Straße (Berlin)", "Müggelheim/Dorf (Berlin)"),
            ("194", "Elsenstr./Kiefholzstr. (Berlin)", "S Ostkreuz Bhf (Berlin)"),
            ("M43", "S Treptower Park (Berlin)",
             "Columbiadamm/Platz der Luftbrücke (Berlin)"),
            ("N96", "Schleusinger Str. (Berlin)", "S+U Wuhletal (Berlin)")
        ]
    }

    def get_random_step() -> Dict[str, Any]:
        step = {}

        vehicle_num = random.randint(0, len(EcoTrekkerUser.vehicles) - 1)
        vehicle = EcoTrekkerUser.vehicles[vehicle_num]
        step.update(
            {
                "vehicle": vehicle
            }
        )

        pair_num = random.randint(0, 3)
        if EcoTrekkerUser.step_pairs.get(vehicle):
            line, start, stop = EcoTrekkerUser.step_pairs.get(vehicle)[
                pair_num]
            step.update(
                {
                    "line": line,
                    "start": start,
                    "end": stop,
                }
            )
        else:
            step.update(
                {
                    "distance": random.randint(3000, 45000)
                }
            )

        return step

    def get_random_route() -> Dict[str, Any]:
        num_steps = random.randint(2, 4)
        steps = []
        for i in range(num_steps):
            steps.append(EcoTrekkerUser.get_random_step())
        return {"steps": steps}

    @task
    def request(self):
        req = {}

        gamification = bool(random.randint(0, 1))
        req.update(
            {
                "gamification": gamification
            }
        )

        num_routes = random.randint(1, 5)
        routes = []
        for i in range(num_routes):
            routes.append(EcoTrekkerUser.get_random_route())
        req.update(
            {
                "routes": routes
            }
        )

        name = f"gamification={gamification.__repr__()}:{num_routes} routes"

        self.client.post("/v1/calc/co2",
                         json=req,
                         name=name)
