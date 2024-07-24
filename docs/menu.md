Command:
`/test`

Arguments:
`name[required]` `lastName`

SubCommands
`kick`,`spawn`,`update`

Use cases:
When Arguments Name and LastName are required then it failed
`/test kick`

When Argument name is required but lastName is not required then it success
`/test <name> kick`

1. Resolve expression
2. Find invoking command
3. trigger event for this command

