# Assignment 3 — Design rationale

## Domain and goal

The system publishes reports about tournament matches. A report can describe a match in progress or summarize a finished match. It can be published to a Telegram channel, an Instagram account, or a newspaper section. This domain is separate from Assignment 1's email Builder project. The output is simulated locally; no external account or API is required to demonstrate the design.

## Why Bridge and Adapter belong together

`MatchReport` is the Bridge Abstraction. `LiveMatchReport` and `FinalMatchReport` are Refined Abstractions with different content rules. `MatchReport` stores a `BroadcastChannel` and calls its `publish` method. The report classes do not know which channel they use. `TelegramBroadcaster` and `InstagramBroadcaster` implement that interface directly. A new report type can extend `MatchReport`; a new channel can implement `BroadcastChannel`. Neither change requires modifying the other hierarchy.

The existing `OldNewspaperPress` cannot implement the common contract without changing its source. Its method is `printArticle(String article, int sectionCode)`, while the application requires `publish(String destination, String content)`. The argument order and destination type differ, and the legacy method reports failure with integer codes (`-1` for an unknown section, `-2` for empty text). `NewspaperBroadcasterAdapter` implements `BroadcastChannel`, translates the destination into a section code, reverses the arguments, and converts every nonzero result and runtime failure into `BroadcastException`. The Abstraction never imports the legacy class or its error codes.

Bridge alone would still leave the incompatible newspaper API outside the channel hierarchy. Adapter alone would connect the newspaper, but it would not separate the two independent dimensions of change: report format and publication channel.

## Required complexity module: dynamic implementor selection

The command-line input supplies the channel name and destination. `Main` demonstrates both report types with the selected channel. `ChannelSelector` discovers `BroadcastChannel` implementations with Java `ServiceLoader`, including the newspaper Adapter, and selects one by its name. The client (`Main`) never chooses a concrete channel with a conditional or constructs one directly. A new channel is registered by adding its class name to the ServiceLoader configuration; the existing report classes and selector stay unchanged. A new report type extends `MatchReport` without changes to the channel classes. Unit tests verify all three channel choices and delegation from both report types.

## Limitation

The direct channel implementations and legacy press print simulated output to the console. They demonstrate composition and error translation, but do not deliver to actual Telegram, Instagram, or a physical newspaper. Real transports would need credentials, network handling, and delivery confirmation without changing the Bridge Abstraction.
