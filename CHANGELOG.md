# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [1.2.3] - 2026-11-18

### Fixed

- when UDP transport is disabled and a client had already sent a SETUP request for UDP transport,  
	and then sends a new SETUP request for TCP transport, the 'main transport settings' for the session  
	were not updated to reflect the new transport type

## [1.2.2] - 2026-11-01

First release after taking this library out of the [Java RTSP Server](https://github.com/tsitle/ts_rtsp_server/) project.
