-module(calling).
-export ([slave/0]).

gettime() ->
    {_,_,Microsecs} = erlang:now(),
    Microsecs.

slave() ->
	receive
		{H1,T1} ->
			I = "intro message",
			lists:map(fun(Child) -> Timestamp = gettime(),Child ! {H1,Child,Timestamp,I} end,T1),
			slave();

		{H1,T1,Timestamp,I} ->
			random:seed(),
			timer:sleep(round(timer:seconds(rand:uniform()))),
			main ! {H1,T1,Timestamp,I},
			T1 ! {H1,T1,Timestamp},
			slave();

		{H1,T1,Timestamp} ->
			random:seed(),
			timer:sleep(round(timer:seconds(rand:uniform()))),
			RepM = "reply message",
			main ! {H1,T1,Timestamp,RepM,RepM},
			slave();
		
		stop ->
            {_,Name} = erlang:process_info(self(), registered_name),
            io:format("~nProcess ~p has received no calls for 1 second, ending...~n~n",[Name]),
            exit(kill)	

	end.