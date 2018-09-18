-module(exchange).
-export ([start/0,master/0]).

start() ->
	{_,List} = file:consult("calls.txt"),
	io:format("****Calls to be made ****~n"),
	printer(List),
	register(list_to_atom("main"),spawn(exchange,master,[])),
	lists:map(fun(Line) -> main ! {Line} end, List),
	timer:sleep(1).

printer([]) ->
	timer:sleep(1);

printer([H|T]) ->	
	{H1,T1} = H,
	io:format("~p:~p~n",[H1,T1]),
	printer(T).

kill_slaves(Line) ->
    lists:map(fun(X) -> {S,_} = X, S ! stop end, Line),
    timer:sleep(1).

master() ->
	receive
		
		{Line} ->
			{H1,T1} = Line,
			register(H1,spawn(calling,slave,[])),
			H1 ! {H1,T1},
			master();   

		{H1,T1,Timestamp,I} ->
			io:format("~p received ~p  from ~p [~p] ~n",[T1,I,H1,Timestamp]),
			master();

		{H1,T1,Timestamp,RepM,RepM} ->
			io:format("~p received ~p from ~p [~p]~n",[H1,RepM,T1,Timestamp]),
			master()

	after
        1500 ->
            {_, Line} = file:consult("calls.txt"),
            kill_slaves(Line),
            io:format("~nMaster has received no replies for 1.5 seconds, ending...~n"),
            exit(kill)
	end.