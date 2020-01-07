# MXM Java Client Library

This project's goal is to build a client library to allow tools and
applications on the JVM to interact with an MXM service.

**Status**: 

- A preliminary version of the library is already available 
  for initial testing (only informational queries at the moment).

- The MXM service itself, including its API, is also in a prototypical stage
  so the API in general is not stable.

## Using the library

The library as well as the javadoc (in a separate JAR) are made available at
[https://bitbucket.org/mbari/mxm-java-client/downloads/](https://bitbucket.org/mbari/mxm-java-client/downloads/).

Besides the `mxm-java-client-x.y.z.jar` library, you will also need the
[Apache httpclient](https://search.maven.org/artifact/org.apache.httpcomponents/httpclient/4.5.10/jar)
and the
[Google Gson](https://search.maven.org/artifact/com.google.code.gson/gson/2.8.6/jar)
libraries in your CLASSPATH.

As indicated in the included [demo program](src/main/java/MxmClientMain.java),
you construct an instance of the `MxmClient` class providing the URL of the MXM endpoint,
and then make queries and requests as needed:

```java
MxmClient mxm = new MxmClient("http://tsauv.shore.mbari.org/mxm-graphql");

// general info about executors:
List<Executor> executors = mxm.getExecutors();
...
```  

Part of the demo program's output is enclosed below.

## General information

The GUI of the current MXM service prototype is at http://tsauv.shore.mbari.org/mxm/.

The corresponding MXM API endpoint is `http://tsauv.shore.mbari.org/mxm-graphql`.

A [GraphiQL](https://github.com/graphql/graphiql) interface is available
at http://tsauv.shore.mbari.org/mxm-graphiql.

## Demo program

The following is part of the output generated by the included demo program 
[`MxmClientMain`](mxm/src/main/java/MxmClientMain.java) when run
against the API endpoint indicated above: 

```
mxm-client library version: 0.1.1

==== all executors response ====
    [{
      "executorId": "TethysDash",
      "description": "TethysDash/LRAUV System",
      "httpEndpoint": "http://tethyssim.shore.mbari.org:8080/TethysDash/api/mxm",
      "apiType": "REST0",
      "canValidate": false,
      "usesSched": true,
      "usesUnits": true
    }]

==== EXECUTOR executorId='TethysDash' ====
    {
      "executorId": "TethysDash",
      "description": "TethysDash/LRAUV System",
      "httpEndpoint": "http://tethyssim.shore.mbari.org:8080/TethysDash/api/mxm",
      "apiType": "REST0",
      "canValidate": false,
      "usesSched": true,
      "usesUnits": true,
      "missionTemplates": [
        {
          "missionTplId": "Science/75m_isobath",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/chlpeak_sampling_InAboveBelow",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/circle_acoustic_contact",
          "description": "This mission runs a circle around a specified acoustic contact. The\ntracking behavior periodically queries the DAT for range and direction\nto the contact, and the DAT acoustically interrogates the contact. The\ntracking component then calculates the position of the contact to update\nthe center of the circle. When the vehicle surfaces and updates its own\nposition with GPS, it queries the contact again, then sends the contact\nposition to shore.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/cork_and_screw",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/cork_and_screw_2",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/depth_sequence_sampling",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/drift",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/follow_1D_front",
          "description": "Search for an upwelling front along a given bearing. When detected, turn\naround a specified distance past the front to track its evolution in\ntime.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/follow_2D_front",
          "description": "Vehicle nominally yo-yo\u0027s zigzagging across an upwelling front, with\nscience turned on. Each time a front edge is detected, the vehicle turns\naround and samples across the front again. Front edge is defined by\ndifference in temperature between two depths.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/front_tracking_2D",
          "description": "Vehicle nominally yo-yo\u0027s zigzagging across an upwelling front, with\nscience turned on. Each time a front edge is detected, the vehicle turns\naround and samples across the front again. Front edge is defined by\ndifference in temperature between several depths.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/isotherm_depth_sampling",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/isotherm_sampling",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/new_follow_2D_front",
          "description": "Vehicle nominally yo-yo\u0027s zigzagging across an upwelling front, with\nscience turned on. Each time a front edge is detected, the vehicle turns\naround and samples across the front again. Front edge is defined by\ndifference in temperature between several depths.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/newer_follow_2D_front",
          "description": "Vehicle nominally yo-yo\u0027s zigzagging across an upwelling front, with\nscience turned on. Each time a front edge is detected, the vehicle turns\naround and samples across the front again. Front edge is defined by\ndifference in temperature between several depths.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/profile_station",
          "description": "This mission yoyos in a circle around a specified location.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/sample_depth_rate",
          "description": "This mission drives in a circle while ascending at a given depth rate.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/sci2",
          "description": "Vehicle yo-yo\u0027s to the specified waypoints, with science turned on.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/sci2_flat_and_level",
          "description": "Vehicle transits to the specified waypoints at the commanded depth (flat-and-level) with science turned on.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/smear_sampling",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/smear_sampling_front",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/smear_waypoint_sampling",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/spiralSample",
          "description": "This mission drives in a circle while sampling at the designated depth.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/spiral_cast",
          "description": "Vehicle spirals downward, then spirals upward, stopping at specified\ndepths to collect samples.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/thermocline",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/trackFigure4",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/trackPatch",
          "description": "Vehicle runs yo-yos, with patch detect enabled for cholorphyll\nconcentration.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/trackPatchFDOM",
          "description": "Vehicle runs yo-yos, with patch detect enabled for FDOM\nconcentration_of_chromophoric_dissolved_organic_matter_in_sea_water\nconcentration.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/trackPatchOil",
          "description": "Vehicle runs at 3m depth (instead of yo-yos since oil concentration varies strongly with depth), \nwith patch detect enabled for oil concentration. Suggested settings for simulaton:\n\nConfig/sim/[username]/Simulator.cfg:\noceanModelData \u003d \"Resources/BurgerOilfieldSpill.nc:mass_concentration_of_petroleum_hydrocarbons_in_sea_water:kg/m3\";\neastCurrent           \u003d 0.05 m/s;    // vel y wrto LV\nnorthCurrent          \u003d 0.005 m/s;    // vel x wrto LV\n\nConfig/sim/[username]/workSite.cfg:\ninitLat        \u003d   71.2225805280196 arcdeg; // Initial latitude\ninitLon        \u003d -163.5093252100575 arcdeg; // Initial longitude\n\nConfig/sim/[username]/sim.cfg:\nWetLabsBB2FL.loadAtStartup         \u003d 0 bool;\n\nWetLabsSeaOWL_UV_A.loadAtStartup         \u003d 1 bool;\nWetLabsSeaOWL_UV_A.simulateSensors         \u003d 1 bool;\nWetLabsSeaOWL_UV_A.serial         \u003d \"SEAOWLA2K-019\";\nWetLabsSeaOWL_UV_A.scaleFactor700 \u003d 3.204e-7 1/m/sr/count;\nWetLabsSeaOWL_UV_A.darkCounts700  \u003d 48 count;\nWetLabsSeaOWL_UV_A.scaleFactorFDOM \u003d 8.096e-3 ppb/count;\nWetLabsSeaOWL_UV_A.darkCountsFDOM  \u003d 50 count;\nWetLabsSeaOWL_UV_A.fdomAccuracy    \u003d 8.19 ppb;\nWetLabsSeaOWL_UV_A.scaleFactorChl \u003d 2.170e-3 ug/l/count;\nWetLabsSeaOWL_UV_A.darkCountsChl  \u003d 50 count;\nWetLabsSeaOWL_UV_A.chlAccuracy    \u003d 1.475 ug/l;\nWetLabsSeaOWL_UV_A.scaleFactorOil \u003d 2.8 ppb/count;\nWetLabsSeaOWL_UV_A.darkCountsOil  \u003d 50 count;\nWetLabsSeaOWL_UV_A.oilAccuracy    \u003d 3.6 ppb;\n\nNavChartDb.charts \u003d \"US1AK90M,US2AK92M,US5AK9NM,US5AK9OM,US5AK9PM,US5AK9QM,US5AK9RM,US5AK9SM,US5AK9TM,US5AK9UM\";",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/trackPatchOil_yoyo",
          "description": "Vehicle yo-yos with patch detect enabled for oil concentration.\n\nNOTE: oil concentrations may vary strongly with depth!\n\nSuggested settings for simulaton:\n\nConfig/sim/[username]/Simulator.cfg:\noceanModelData \u003d \"Resources/BurgerOilfieldSpill.nc:mass_concentration_of_petroleum_hydrocarbons_in_sea_water:kg/m3\";\neastCurrent           \u003d 0.05 m/s;    // vel y wrto LV\nnorthCurrent          \u003d 0.005 m/s;    // vel x wrto LV\n\nConfig/sim/[username]/workSite.cfg:\ninitLat        \u003d   71.2225805280196 arcdeg; // Initial latitude\ninitLon        \u003d -163.5093252100575 arcdeg; // Initial longitude\n\nConfig/sim/[username]/sim.cfg:\nWetLabsBB2FL.loadAtStartup         \u003d 0 bool;\n\nWetLabsSeaOWL_UV_A.loadAtStartup         \u003d 1 bool;\nWetLabsSeaOWL_UV_A.simulateSensors         \u003d 1 bool;\nWetLabsSeaOWL_UV_A.serial         \u003d \"SEAOWLA2K-019\";\nWetLabsSeaOWL_UV_A.scaleFactor700 \u003d 3.204e-7 1/m/sr/count;\nWetLabsSeaOWL_UV_A.darkCounts700  \u003d 48 count;\nWetLabsSeaOWL_UV_A.scaleFactorFDOM \u003d 8.096e-3 ppb/count;\nWetLabsSeaOWL_UV_A.darkCountsFDOM  \u003d 50 count;\nWetLabsSeaOWL_UV_A.fdomAccuracy    \u003d 8.19 ppb;\nWetLabsSeaOWL_UV_A.scaleFactorChl \u003d 2.170e-3 ug/l/count;\nWetLabsSeaOWL_UV_A.darkCountsChl  \u003d 50 count;\nWetLabsSeaOWL_UV_A.chlAccuracy    \u003d 1.475 ug/l;\nWetLabsSeaOWL_UV_A.scaleFactorOil \u003d 2.8 ppb/count;\nWetLabsSeaOWL_UV_A.darkCountsOil  \u003d 50 count;\nWetLabsSeaOWL_UV_A.oilAccuracy    \u003d 3.6 ppb;\n\nNavChartDb.charts \u003d \"US1AK90M,US2AK92M,US5AK9NM,US5AK9OM,US5AK9PM,US5AK9QM,US5AK9RM,US5AK9SM,US5AK9TM,US5AK9UM\";",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/trackPatchRhod",
          "description": "Vehicle runs yo-yos, with patch detect enabled for cholorphyll\nconcentration.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        },
        {
          "missionTplId": "Science/trackStarOil",
          "description": "Vehicle tracks patch identified by oil concentration.",
          "assetClasses": [
            {
              "assetClassName": "LRAUV"
            }
          ]
        }
      ]
    }
```
