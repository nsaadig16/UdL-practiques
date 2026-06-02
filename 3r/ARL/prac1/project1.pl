% =============== PROJECT 1 ===============
% Naïm Saadi Gallego & Eduard Térmens Botanch
% =========================================

% Knowledge base
similar_genre("Fantasy","Adventure").
similar_genre("Crime", "Drama").
similar_genre("Romance", "Comedy").
similar_genre("SciFi", "Fantasy").

series("edunai4",["Comedy"],14).
series("TheWire",["Action","Crime"],5).
series("edunai5",["Crime","Action"],7).
series("edunai7",["Fantasy"],2).
series("RedDwarf",["Comedy","SciFi"],10).
series("edunai6",["Drama","SciFi"],1).
series("edunai8",["Fantasy"],3).
series("MoonLighting",["Romance","Crime"],5).
series("edunai3",["Comedy"],10).
series("edunai9",["Adventure"],3).

% =========================================
% Problem 1
% =========================================
% Will be true if both S1 and S2 are different TV series
% but such that CGenre is a common genre for S1 and S2
matchGenre(S1,S2,CGenre):-
   series(S1, Genres1,_), % define S1 & Genres1
   series(S2, Genres2,_), % define S2 & Genres2
   S1 \= S2, % Makes sure S1 is different from S2
   member(CGenre, Genres1), % Checks if the Genre is in S1
   member(CGenre, Genres2). % Checks if the Genre is in S2

% Will be true if N1 and N2 are numbers such that the distance between them is at most Dist
similarNumber(N1,N2,Dist):-
    X is abs(N1 - N2), % Defines X as the absolute value of the difference
    X =< Dist. % Checks if the difference is lesser or equal

% Will be true if matchGenre(S1, S2, CGenre) is true and in addition 
% the number of seasons of S1 (NS1) and the number of seasons of S2 (NS2)
% satisfy the predicate similarNumber(NS1, NS2, Dist)
matchGenreAndSeasons(S1, S2, CGenre, Dist):-
    matchGenre(S1,S2,CGenre), % Checks if the genre matches
    series(S1, _, N1), % Defines S1 & N1
    series(S2, _, N2), % Defines S2 & N2
    similarNumber(N1,N2,Dist). % Checks if they are similar numbers

% =========================================
% Problem 2
% =========================================
% Will be true if S1 is a series with G1 as one of its genres,
% S2 is a series with G2 as one of its genres, they are different series,
% and G1 is a subgenre of G2
matchSimGenre(S1, S2, G1, G2) :-
    series(S1, Genres1, _), % Defines S1 & Genres1
    series(S2, Genres2, _), % Defines S2 & Genres2
    member(G1, Genres1), % Checks if G1 is in Genres1
    member(G2, Genres2), % Checks if G2 is in Genres2
    similar_genre(G1, G2). % Checks if G1 and G2 are a similar genre

% =========================================
% Problem 3
% =========================================
%  Will true if either:
% - S1 is a series with G1 as one of its genres, S2 is a series with G2 as one of its genres,
%   and G1 is a subgenre of G2.
% - S1 is a series with G1 as one of its genres, G1 is a subgenre of a third one GI, and for a third
%   series SI recSimGenre(SI, S2, GI, G2) is true.
recSimGenre(S1,S2,G1,G2) :-
    matchSimGenre(S1,S2,G1,G2) ; % S1 and S2 have similar genre 
    % OR
    series(S1,Genre1,_), % Defines S1 & Genre1
    series(S2,_,_), % Defines S2
    series(SI,_,_), % Defines S1
    member(G1, Genre1), % Checks if G1 is in Genre1
    similar_genre(G1,GI), % Checks if G1 is similar to GI
    recSimGenre(SI,S2,GI,G2). % Makes the recursive call