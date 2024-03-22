from typing import Any, Dict
from locust import HttpUser, task, events
import random

random.seed(None)

@events.request.add_listener
def my_request_handler(request_type, name, response_time, response_length, response,
                       context, exception, start_time, url, **kwargs):
    if exception:
        print(f"Request to {name} failed with exception {exception}")
        print(f"response: {response}")
        print(f"Body: {context}")


class EcoTrekkerUser(HttpUser):

    vehicles = [
        "/car", "/car/e-car", "/car/ice-car",
        "/bike", "/bike/e-bike", "/bike/bicycle",
        "/tram", "/metro", "/train",
        "/bus", "/bus/e-bus", "/bus/ice-bus",
    ]

    bus_pairs = [
        ("169", "Semliner Straße (Berlin)", "Müggelheim/Dorf (Berlin)"),
        ("194", "Elsenstr./Kiefholzstr. (Berlin)", "S Ostkreuz Bhf (Berlin)"),
        ("M43", "S Treptower Park (Berlin)",
         "Columbiadamm/Platz der Luftbrücke (Berlin)"),
        ("N96", "Schleusinger Str. (Berlin)", "S+U Wuhletal (Berlin)"),
        ("363", "Krankenhaus Hedwigshöhe (Berlin)", "S Grünau (Berlin)"),
        ("161", "S Erkner Bhf/ZOB", "Rahnsdorf/Waldschänke (Berlin)"),
        ("N43", "U Blissestr./Uhlandstr. (Berlin)",
         "Haus des Rundfunks (Berlin)"),
        ("M43", "S Ostkreuz Bhf (Berlin)", "Sommerbad Neukölln (Berlin)"),
        ("143", "Berlin, Rubensstr./S Friedenau", "S Halensee (Berlin)"),
        ("744", "S Schönefeld (bei Berlin) Bhf", "U Rudow (Berlin)"),
        ("300", "Berlin, East Side Gallery", "U Rotes Rathaus (Berlin)"),
        ("365", "S Baumschulenweg (Berlin)",
         "Siemensstr./Nalepastr. (Berlin)"),
        ("310", "U Adenauerplatz (Berlin)", "S+U Heidelberger Platz (Berlin)"),
        ("396", "S Karlshorst (Berlin)", "U Friedrichsfelde (Berlin)"),
        ("395", "S Mahlsdorf Bhf (Berlin)", "U Hönow (Berlin)"),
        ("390", "Ahrensfelde, Bahnhof Friedhof", "S Ahrensfelde Bhf (Berlin)"),
        ("380", "Siemensstr./Halskestr. (Berlin)",
         "S+U Rathaus Steglitz (Berlin)"),
        ("377", "Lahnstr./U Neukölln (Berlin)", "Kranoldstr. (Berlin)"),
        ("369", "Gosen, Müggelpark", "Berlin, Alt-Müggelheim"),
        ("349", "S Grunewald (Berlin)", "U Theodor-Heuss-Platz (Berlin)"),
        ("334", "Habichtswald (Berlin)", "Alt-Gatow (Berlin)"),
        ("326", "Olafstr. (Berlin)", "S Hermsdorf (Berlin)"),
        ("320", "U Paracelsus-Bad/Aroser Allee (Berlin)", "Flottenstr. (Berlin)"),
        ("316", "Schloss Glienicke (Berlin)", "S Wannsee Bhf (Berlin)"),
        ("296", "S+U Lichtenberg Bhf (Berlin)", "Köpenicker Allee (Berlin)"),
        ("294", "S Gehrenseestr. (Berlin)", "Marzahner Str. (Berlin)"),
        ("284", "Mercatorweg (Berlin)", "S Lichterfelde Ost Bhf (Berlin)"),
        ("275", "Birkenhaag (Berlin)", "Cecilienstr./Blohmstr. (Berlin)"),
        ("269", "S Kaulsdorf (Berlin)", "Hoppendorfer Str. (Berlin)"),
        ("259", "Stadion Buschallee/Hansastr. (Berlin)", "Hansastr. (Berlin)")
    ]

    step_pairs = {
        "/tram": [
            ("M8", "Niemegker Str. (Berlin)",
             "Allee der Kosmonauten/Rhinstr. (Berlin)"),
            ("M6", "U Alexanderplatz (Berlin) [Tram]",
             "Gewerbepark Georg Knorr (Berlin)"),
            ("M4", "Berlin, Am Friedrichshain",
             "Landsberger Allee/Weißenseer Weg (Berlin)"),
            ("M2", "Knaackstr. (Berlin)", "Falkenberger Str./Berliner Allee (Berlin)"),
            ("60", "Bruno-Wille-Str. (Berlin)", "Alte Försterei (Berlin)"),
            ("60", "Haeckelstr. (Berlin)", "Gelnitzstr. (Berlin)"),
            ("61", "S Schöneweide/Sterndamm (Berlin)",
             "Wassersportzentrum (Berlin)"),
            ("27", "Pablo-Neruda-Str. (Berlin)",
             "Falkenberger Str./Berliner Allee (Berlin)"),
            ("M17", "Falkenberg (Berlin)", "Meeraner Str. (Berlin)"),
            ("M10", "U Naturkundemuseum (Berlin)",
             "Friedrich-Ludwig-Jahn-Sportpark (Berlin)"),
            ("M1", "U Oranienburger Tor (Berlin)", "Pankow Kirche (Berlin)"),
            ("27", "Bahnhofstr./Lindenstr. (Berlin)", "S Karlshorst (Berlin)"),
            ("68", "Marktplatz Adlershof (Berlin)", "Berlin, Wassersportallee"),
            ("67", "Krankenhaus Köpenick/Südseite (Berlin)", "S Adlershof (Berlin)"),
            ("63", "S Adlershof (Berlin)", "Schloßplatz Köpenick (Berlin)"),
            ("62", "S Köpenick (Berlin)", "Berlin, Betriebshof Köpenick"),
            ("61", "S Schöneweide Bhf (Berlin)", "Wassermannstr. (Berlin)"),
            ("60", "Marktplatz Friedrichshagen (Berlin)",
             "Bahnhofstr./Seelenbinderstr. (Berlin)"),
            ("50", "U Osloer Str. (Berlin)", "S Pankow-Heinersdorf (Berlin)"),
            ("37", "S Schöneweide Bhf (Berlin)", "Gotlindestr. (Berlin)"),
            ("21", "Betriebshof Lichtenberg (Berlin)", "S Rummelsburg (Berlin)"),
            ("18", "U Hellersdorf (Berlin)", "S Landsberger Allee (Berlin)"),
            ("12", "U Oranienburger Tor (Berlin)", "Berlin, Pasedagplatz")
        ],
        "/metro": [
            ("U8", "U Leinestr. (Berlin)", "S+U Wittenau (Berlin)"),
            ("U7", "U Lipschitzallee (Berlin)", "U Fehrbelliner Platz (Berlin)"),
            ("U9", "U Leopoldplatz (Berlin)", "U Friedrich-Wilhelm-Platz (Berlin)"),
            ("U2", "U Gleisdreieck (Berlin)", "U Olympia-Stadion (Berlin)"),
            ("U9", "S+U Rathaus Steglitz (Berlin)",
             "S+U Zoologischer Garten Bhf (Berlin)"),
            ("U8", "U Hermannplatz (Berlin)", "S+U Wittenau (Berlin)"),
            ("U7", "U Grenzallee (Berlin)", "S+U Yorckstr. (Berlin)"),
            ("U6", "S+U Tempelhof (Berlin)", "S+U Friedrichstr. Bhf (Berlin)"),
            ("U5", "S+U Berlin Hauptbahnhof", "S+U Frankfurter Allee (Berlin)"),
            ("U4", "S+U Innsbrucker Platz (Berlin)", "U Nollendorfplatz (Berlin)"),
            ("U3", "U Freie Universität (Thielplatz) (Berlin)",
             "S+U Warschauer Str. (Berlin)"),
            ("U1", "S+U Warschauer Str. (Berlin)", "U Kurfürstendamm (Berlin)"),
        ],
        "/train": [
            ("S3", "S Köpenick (Berlin)", "S Tiergarten (Berlin)"),
            ("S8", "S Wildau", "S+U Pankow (Berlin)"),
            ("S42", "S Westend (Berlin)", "S Landsberger Allee (Berlin)"),
            ("S1", "S Borgsdorf", "S+U Yorckstr. (Großgörschenstr.) (Berlin)"),
            ("S85", "S Bornholmer Str. (Berlin)", "S Treptower Park (Berlin)"),
            ("S8", "S+U Wedding (Berlin)", "S Ostkreuz Bhf (Berlin)"),
            ("S42", "S+U Jungfernheide Bhf (Berlin)", "S+U Wedding (Berlin)"),
            ("S26", "S+U Gesundbrunnen Bhf (Berlin)", "S Teltow Stadt"),
            ("S41", "S+U Schönhauser Allee (Berlin)", "S+U Bundesplatz (Berlin)"),
            ("S7", "S Potsdam Hauptbahnhof", "S+U Alexanderplatz Bhf (Berlin)"),
            ("S46", "S+U Bundesplatz (Berlin)", "S Baumschulenweg (Berlin)"),
            ("S45", "Flughafen BER", "S Südkreuz Bhf (Berlin)"),
            ("S2", "S+U Pankow (Berlin)", "S Südkreuz Bhf (Berlin)"),
            ("S9", "S Olympiastadion (Berlin)", "S Adlershof (Berlin)"),
            ("S47", "S Baumschulenweg (Berlin)", "S Spindlersfeld (Berlin)"),
            ("S5", "S Charlottenburg Bhf (Berlin)", "S Ostbahnhof (Berlin)"),
            ("S3", "S Spandau Bhf (Berlin)", "S+U Friedrichstr. Bhf (Berlin)")
        ],
        "/bus": bus_pairs,
        "/bus/e-bus": bus_pairs,
        "/bus/ice-bus": bus_pairs
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

        
        if EcoTrekkerUser.step_pairs.get(vehicle):
            pair_num = random.randint(0, len(EcoTrekkerUser.step_pairs) - 1)
            reverse = random.randint(0, 1) == 1
            line, start, stop = EcoTrekkerUser.step_pairs.get(vehicle)[
                pair_num]

            # if reverse:
            #     start, stop = stop, start
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
        num_steps = random.randint(3, 6)
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

        num_routes = random.randint(8, 20)
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
                         context=req,
                         name=name)
