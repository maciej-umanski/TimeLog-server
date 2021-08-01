# TimeLog-server 0.0.4

``` login {login} {password} ```

Expected response : __User__ object

Possible responses : __Code__ object

``` register {login} {password} ```

Only responses : __Code__ object

``` logoff ```

Only responses : __Code__ object

``` connection ```

Only responses : __Code__ object

``` task ```

Only responses : __Task__ object

``` whoami ```

Expected response : __User__ object

Possible responses : __Code__ object

``` {User} ```

If user.id matches id of logged user in client; user.name, surname and email will be updated in databse.

Expected response : __User__ object

Possible responses : __Code__ object