# Vex

[![Build and Test](https://github.com/ManApart/vex/actions/workflows/runTests.yml/badge.svg)](https://github.com/ManApart/vex/actions/workflows/runTests.yml)

Essentially forked / branched from [an older repo](https://github.com/ManApart/vex) when the GDX (JInput) seemed to not recognize Windows 10 and then crash on startup.

## Running

```
runJvm
runJs
jsBrowserDistribution
```

## Pushing to web

```
aws s3 sync build/distributions/ s3://austinkucera.com/games/vex/
```

## Credits
https://rvros.itch.io/animated-pixel-hero
https://edermunizz.itch.io/free-pixel-art-forest
https://edermunizz.itch.io/free-pixel-art-overworld-tileset
https://edermunizz.itch.io/free-pixel-art-plataformer-painted-style
https://bakudas.itch.io/generic-platformer-pack
https://trixelized.itch.io/starstring-fields
https://trixelized.itch.io/kinda-cute-forest
https://trixelized.itch.io/nightfield
https://pixelhole.itch.io/pixelholes-overworld-tileset
https://szadiart.itch.io/platformer-fantasy-set1
https://merchant-shade.itch.io/16x16-mini-world-sprites

character - 36x39

## TODO



Vex
World map like mario
Many exits per level
Climb only tiles
stamina?

walljumping should not let you move for x amount of time.
prevent wall jumping up a single wall



onCollided(direction)
onNoLongerCollided(direction)
isCollided(direction)
Store collided directions so can check and see what currently colliding with
isNear(direction)?
Same as is collidded but has a buffer (say .2f)


Button
IsPressed
IsFirstFramePressed
IsFirstFrameReleased
StateTime

Axis
Value (float -1 to 1)
Active threshold (ex .1)
IsActive
IsFirstFrameActive
IsFirstFrameInactive
StateTime


Move kit
Grappling Hook
Aim with right stick
Right trigger shoots
Left trigger quickly reels in
Wall run/Wall Jump
Left trigger while against wall runs up it a vertical distance, can end in a wall jump
Can end early by pressing jump
If trigger let go, will stop running up and start falling back down without jump
Jump/Double Jump (a button)
If done against wall will do a wall jump
Dash (left/right bumper)
Always dash either left or right. Stop momentum
Slide down slopes?


Precedence
Grapple > Zip > Jump > Dash

Levels (metroidvania)
Air jets
Ice floors
Swing on ropes to unlock razer rope (grappling hook)
