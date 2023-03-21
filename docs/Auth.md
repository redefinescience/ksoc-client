TODO: Finish, and use proper markdown!

Authentication:

Authorization:

AuthRepository:

The Auth repository is a means of storing auth tokens, and info about the current user.

Each platform implements a specific AuthStore for persistence of this information.

Both tokens and current user info are streamable via Flow, for reactive login state.
Tokens = null && user = null -- User not logged in.
Tokens != null && user == null -- Logging in (ie: fetching user info).
Tokens != null && user != null -- User logged in.
