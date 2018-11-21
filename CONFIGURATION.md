# Configuration

Andesite can be configured from 3 sources

- System environment variables
- JVM system properties
- A JSON file named `config.json`¹ in the application working directory

Keys are loaded by default in the order `file, system properties, environment variables`²


¹ This name can be changed with the `config-file` setting (file is ignored when reading this setting).

² This order can be changed with the `andesite.config.load-order` JVM system property, as a comma separated list of
the sources to use (valid sources are `file`, `props` and `env`).

## System properties

System properties are read from the `andesite.<name>` JVM system property.

Examples:

| setting | system property |
|---------|-----------------|
| x | andesite.x |
| x.y | andesite.x.y |
| x.y-z | andesite.x.y-z |

## Environment variables

Environment variables are read from the `ANDESITE_<name, uppercase, dots and slashes replaced with underscores>` variable.

Example:

| setting | system property |
|---------|-----------------|
| x | ANDESITE_X |
| x.y | ANDESITE_X_Y |
| x.y-z | ANDESITE_X_Y_Z |

# Settings

| key | type | description | default |
|-----|------|-------------|---------|
| password | string | password to use for http/websocket access. No filtering is done if null | null |
| lavalink.ws-path | string | route to run the lavalink websocket on. | /lavalink |
| send-system.type* | string | type of send system to use. Valid options are `nio`, `jda` and `nas` | nas |
| send-system.async | boolean | whether or not to use jda-async-packet-provider to wrap the send system | false |
| send-system.nas-buffer | integer | buffer duration, in milliseconds, to keep in native code. Ignored if type isn't `nas` | 400 |
| send-system.non-allocating | boolean | whether or not to use the non allocating frame buffer | false |
| node.region | string | region of the node | null |
| node.id | string | id of the node | null |
| transport.http.port | integer | port to run the http/websocket server | 5000 |
| transport.http.rest | boolean | whether or not to enable the http api | true |
| transport.http.ws | boolean | whether or not to enable the websocket api | true |
| transport.singyeong.enabled | boolean | whether or not to enable the singyeong api | false |
| transport.singyeong.url | string | url of the singyeong server | null |
| transport.singyeong.app-id | string | application id to register the node with | andesite-audio |
| source.bandcamp | boolean | whether or not to enable playing and resolving tracks from bandcamp | false |
| source.beam | boolean | whether or not to enable playing and resolving tracks from beam | false |
| source.http | boolean | whether or not to enable playing and resolving tracks from http urls | false |
| source.local | boolean | whether or not to enable playing and resolving tracks from local files | false |
| source.soundcloud | boolean | whether or not to enable playing and resolving tracks from soundcloud | false |
| source.twitch | boolean | whether or not to enable playing and resolving tracks from twitch | false |
| source.vimeo | boolean | whether or not to enable playing and resolving tracks from vimeo | false |
| source.youtube | boolean | whether or not to enable playing and resolving tracks from youtube | false |

\* When running on architectures not supported by [jda-nas](https://github.com/sedmelluq/jda-nas), such as
ARM or Darwin devices, you must use either `jda` or `nio` for the send system. For production, nio is preferred
as it doesn't spawn a thread per voice connection.