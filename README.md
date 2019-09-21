This project's goal is to build a java client library to allow tools and
applications to interact with an MXM service.

The GUI of the current MXM service is at http://tsauv.shore.mbari.org/mxm/.

A [GraphiQL](https://github.com/graphql/graphiql) interface is available
at http://tsauv.shore.mbari.org/mxm-graphiql.

The corresponding MXM API endpoint is `http://tsauv.shore.mbari.org/mxm-graphql`.

*Status*: A preliminary version of the library is already 
[available](https://bitbucket.org/mbari/mxm-java-client/downloads/)
for initial testing (only informational queries at the moment).

The following is the output of the included demo program 
[`MxmClientMain`](mxm/src/main/java/MxmClientMain.java) when run
against the API endpoint indicated above: 

```
==== all executors response ====
    [{
      "executorId": "TFT",
      "description": "TSAUV Front Tracking Program"
    }, {
      "executorId": "dd",
      "description": "ddd"
    }]

==== EXECUTOR executorId='TFT' ====
    {
      "executorId": "TFT",
      "description": "TSAUV Front Tracking Program",
      "missionTemplates": [
        {
          "missionTplId": "tft",
          "description": "**Wave glider temperature front detection and tracking**\nThe overall algorithm consists of two main parts: _Front detection_: this is only concerned with detecting fronts given a stream of timestamped, geo-located temperature samples. _Front tracking_: Triggered by detected fronts, the overall tracking adds logic to issue new waypoints so the waveglider traverses the temperature front in a zigzag pattern.\n",
          "assetClasses": [
            {
              "assetClassName": "Waveglider"
            }
          ]
        },
        {
          "missionTplId": "tft-advanced",
          "description": "**Do not use** (yet).\nThis is intended for tracking exercises against specific Wave Gliders that haven\u0027t been pre-configured in this front tracking service. This is to be done by exposing all necessary Wave Glider parameters.\n",
          "assetClasses": [
            {
              "assetClassName": "Waveglider"
            }
          ]
        }
      ]
    }

==== MISSION TEMPLATE missionTplId='tft' ====
    {
      "missionTplId": "tft",
      "description": "**Wave glider temperature front detection and tracking**\nThe overall algorithm consists of two main parts: _Front detection_: this is only concerned with detecting fronts given a stream of timestamped, geo-located temperature samples. _Front tracking_: Triggered by detected fronts, the overall tracking adds logic to issue new waypoints so the waveglider traverses the temperature front in a zigzag pattern.\n",
      "assetClasses": [
        {
          "assetClassName": "Waveglider"
        }
      ],
      "parameters": [
        {
          "paramName": "boundary_buffer_degrees",
          "type": "float",
          "defaultValue": "-0.004",
          "required": true,
          "description": "Distance (in degrees) to compute the inner \"soft\" boundary from the given `boundary_polygon`.\n"
        },
        {
          "paramName": "boundary_polygon",
          "type": "Polygon",
          "defaultValue": "[ [36.850, -122.04], [36.850, -121.843], [36.734, -121.843], [36.734, -122.04] ]",
          "required": true,
          "description": "A [convex polygon](https://en.wikipedia.org/wiki/Convex_polygon) that determines a boundary for purposes of commanding the Wave glider so it stays within such polygon. The tracking algorithm gets activated only while current reported positions lie within this polygon.\nExample: ``` [ [36.850, -122.04], [36.850, -121.843], [36.734, -121.843], [36.734, -122.04] ] ```\n"
        },
        {
          "paramName": "commanding_enabled",
          "type": "boolean",
          "defaultValue": "true",
          "required": false,
          "description": "Indicates whether actual commanding to the Waveglider is enabled. The generated `gotoWatch` commands will only be logged out when such commanding is disabled.\n"
        },
        {
          "paramName": "continue_flight_time_mins",
          "type": "float",
          "defaultValue": "30",
          "required": true,
          "description": "Time in minutes to continue flying after a front is detected.\n"
        },
        {
          "paramName": "delta_time_secs",
          "type": "float",
          "defaultValue": "300",
          "required": true,
          "description": "CTD data stream comes in bursts. Each burst contains a few very dense samples, but the burst-to-burst interval is typically several minutes. The algorithm only takes in samples that are separated more than delta_time_secs, and ignores very-near neighbors in the same burst.\n"
        },
        {
          "paramName": "distance_threshold_meters",
          "type": "float",
          "defaultValue": "2000",
          "required": true
        },
        {
          "paramName": "notifications_enabled",
          "type": "boolean",
          "defaultValue": "true",
          "required": false,
          "description": "Indicates whether front detection notifications to `auvtrack@mbari.org` and Slack are enabled.\n"
        },
        {
          "paramName": "platform_name",
          "type": "string",
          "defaultValue": "wgTTooFr",
          "required": true,
          "description": "Name used to report front detections to the tracking database.\n"
        },
        {
          "paramName": "temperature_threshold",
          "type": "float",
          "defaultValue": "0.3",
          "required": true,
          "description": "Threshold for the accumulated distance according to average window temperature.\n"
        },
        {
          "paramName": "time_end",
          "type": "string",
          "required": false,
          "description": "String indicating an upper time limit for data request. Can start with `-` to indicate a time offset wrt current time, or can be ISO-8601. By default each request will have current time as the upper limit.\n"
        },
        {
          "paramName": "time_start",
          "type": "string",
          "required": false,
          "description": "String indicating time for the very first request for data. Can start with `-` to indicate a time offset wrt current time, or can be ISO-8601. By default, `-10m` (10 minutes ago).\n"
        },
        {
          "paramName": "variable_name",
          "type": "string",
          "defaultValue": "temperature",
          "required": true,
          "description": "The name of the target variable for detection.\n"
        },
        {
          "paramName": "variable_units",
          "type": "string",
          "defaultValue": "degC",
          "required": true,
          "description": "The units corresponding to `variable_name`. This is mainly used for informational purposes.\n"
        },
        {
          "paramName": "window_size_meters",
          "type": "float",
          "defaultValue": "1000",
          "required": true,
          "description": "Each completed window will have all samples within this distance plus at most one sample beyond this distance with respect to the oldest sample in the window.\n"
        },
        {
          "paramName": "zigzag_heading1",
          "type": "float",
          "defaultValue": "270",
          "required": true,
          "description": "`zigzag_heading1` and `zigzag_heading2` define the desired zigzag pattern. Upon being enabled, the tracking will issue an initial waypoint calculated as the intersection of a line with heading indicated by `zigzag_heading1` from the very first notified position (that lies within the reduced polygon) and the bounding polygon. The subsequent waypoint will be calculated in a similar manner but with `zigzag_heading2` as the heading; and so on by switching these two parameters. The exception to this general logic happens when the vehicle hits the north or south walls, in which case the headings are \"reversed.\"\n"
        },
        {
          "paramName": "zigzag_heading2",
          "type": "float",
          "defaultValue": "75",
          "required": true,
          "description": "`zigzag_heading1` and `zigzag_heading2` define the desired zigzag pattern. Upon being enabled, the tracking will issue an initial waypoint calculated as the intersection of a line with heading indicated by `zigzag_heading1` from the very first notified position (that lies within the reduced polygon) and the bounding polygon. The subsequent waypoint will be calculated in a similar manner but with `zigzag_heading2` as the heading; and so on by switching these two parameters. The exception to this general logic happens when the vehicle hits the north or south walls, in which case the headings are \"reversed.\"\n"
        }
      ]
    }

==== missions for missionTplId='tft' ====
    [{
      "executorId": "TFT",
      "missionTplId": "tft",
      "missionId": "m-sdsd",
      "assetId": "Tiny",
      "description": "maids aoishj asudh oiu"
    }]

==== MISSION missionId='m-sdsd' ====
    {
      "executorId": "TFT",
      "missionTplId": "tft",
      "missionId": "m-sdsd",
      "assetId": "Tiny",
      "description": "maids aoishj asudh oiu",
      "arguments": [
        {
          "paramName": "boundary_polygon",
          "paramValue": "[[36.91844,-122.073475],[36.873729,-122.119571],[36.715211,-121.907644],[36.926532,-121.957448],[36.926532,-121.957448]]"
        }
      ]
    }

==== MISSION TEMPLATE missionTplId='tft-advanced' ====
    {
      "missionTplId": "tft-advanced",
      "description": "**Do not use** (yet).\nThis is intended for tracking exercises against specific Wave Gliders that haven\u0027t been pre-configured in this front tracking service. This is to be done by exposing all necessary Wave Glider parameters.\n",
      "assetClasses": [
        {
          "assetClassName": "Waveglider"
        }
      ],
      "parameters": [
        {
          "paramName": "vid",
          "type": "string",
          "required": false,
          "description": "If given, this should be the vehicle ID for Firehose data retrieval. Otherwise, WgmsClient is used for data retrieval with the given `view_id`.\n**Note**: Only one of `vid` and `view_id` must be provided.\n"
        },
        {
          "paramName": "view_id",
          "type": "string",
          "required": false,
          "description": "For data rertieval using WgmsClient. Required if `vid` is not given.\n**Note**: Only one of `vid` and `view_id` must be provided.\n"
        },
        {
          "paramName": "wcs_vehicle_id",
          "type": "string",
          "required": true,
          "description": "Vehicle ID to issue commands via `wcs`.\n"
        }
      ]
    }

==== missions for missionTplId='tft-advanced' ====
    [{
      "executorId": "TFT",
      "missionTplId": "tft",
      "missionId": "m-sdsd",
      "assetId": "Tiny",
      "description": "maids aoishj asudh oiu"
    }]

==== MISSION missionId='m-sdsd' ====
    not found

==== EXECUTOR executorId='dd' ====
    {
      "executorId": "dd",
      "description": "ddd",
      "missionTemplates": [
        {
          "missionTplId": "mt1",
          "description": "mt1 descr",
          "assetClasses": [
            {
              "assetClassName": "ac1"
            }
          ]
        }
      ]
    }

==== MISSION TEMPLATE missionTplId='mt1' ====
    {
      "missionTplId": "mt1",
      "description": "mt1 descr",
      "assetClasses": [
        {
          "assetClassName": "ac1"
        }
      ],
      "parameters": [
        {
          "paramName": "p1",
          "type": "float",
          "defaultValue": "0.1",
          "required": true,
          "description": "- alks alsk alks aldsk\n- lqksd"
        },
        {
          "paramName": "p2",
          "type": "Point",
          "required": true,
          "description": "point"
        },
        {
          "paramName": "poly1",
          "type": "Polygon",
          "defaultValue": "[[36.888614,-122.030666],[36.766212,-122.071071],[36.755824,-121.953424],[36.755824,-121.953424]]",
          "required": true,
          "description": "polygpn"
        }
      ]
    }

==== missions for missionTplId='mt1' ====
    [{
      "executorId": "dd",
      "missionTplId": "mt1",
      "missionId": "m1",
      "assetId": "a1",
      "description": "- Aliqua in officia cillum in esse nostrud voluptate incididunt. \n- Tempor ullamco mollit reprehenderit cupidatat sunt ea reprehenderit in proident dolor consectetur. \n- Ea enim laboris ut incididunt irure culpa officia."
    }]

==== MISSION missionId='m1' ====
    {
      "executorId": "dd",
      "missionTplId": "mt1",
      "missionId": "m1",
      "assetId": "a1",
      "description": "- Aliqua in officia cillum in esse nostrud voluptate incididunt. \n- Tempor ullamco mollit reprehenderit cupidatat sunt ea reprehenderit in proident dolor consectetur. \n- Ea enim laboris ut incididunt irure culpa officia.",
      "arguments": [
        {
          "paramName": "p2",
          "paramValue": "[36.804998,-121.972034]"
        },
        {
          "paramName": "poly1",
          "paramValue": "[[36.883911,-122.112367],[36.751823,-122.163452],[36.760058,-122.006553],[36.811484,-121.922075],[36.811484,-121.922075]]"
        }
      ]
    }
```
