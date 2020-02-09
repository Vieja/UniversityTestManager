-- -- noinspection SqlNoDataSourceInspectionForFile
--
-- create or replace procedure zaktualizuj_ocene1
-- (dataEgz in DATE) is
-- begin
--     update studenci s
--     set (s.ocena_1) = (
--         select p.ocena
--         from studenci ss join podejscia p on (ss.indeks = p.indeks)
--         where s.indeks = ss.indeks)
--     where s.indeks in (
--         select ss.indeks
--         from studenci ss, podejscia p, zestawy z
--         where ss.indeks = p.indeks and z.id_zes = p.id_zes and
--                 z.data_egz = dataEgz and z.termin = 'pierwszy');
-- end zaktualizuj_ocene1;
--
--
-- create or replace procedure zaktualizuj_ocene2
-- (dataEgz in DATE) is
-- begin
--     update studenci s
--     set (s.ocena_2) = (
--         select p.ocena
--         from studenci ss join podejscia p on (ss.indeks = p.indeks)
--         where s.indeks = ss.indeks)
--     where s.indeks in (
--         select ss.indeks
--         from studenci ss, podejscia p, zestawy z
--         where ss.indeks = p.indeks and z.id_zes = p.id_zes and
--               z.data_egz = dataEgz and z.termin != 'pierwszy');
-- end zaktualizuj_ocene2;
-- /
--
-- create or replace procedure usun_tych_co_zdali is
-- begin
--     delete from studenci
--     where (ocena_1 is not null and ocena_1!='2.0') or (ocena_1 = '2.0' and ocena_2 is not null and ocena_2!='2.0');
-- end usun_tych_co_zdali;
-- /

--CREATE OR REPLACE FUNCTION ilePunktowMaZestaw
--    (idZes IN NATURAL)
--RETURN NUMBER IS
--    vPunkty NUMBER;
--BEGIN
-- SELECT SUM(punkty)
-- INTO vPunkty
-- FROM zadania zad, zawartosc zaw
-- WHERE zad.id_zad = zaw.id_zad and zaw.id_zes = idZes;
--RETURN vPunkty;
--END ilePunktowMaZestaw;
--
--declare
-- vIle NUMBER;
--begin
--vIle := ilePunktowMaZestaw(1);
--end;



-- begin
-- --zaktualizuj_ocene1(to_date('15-12-2019','DD-MM-YYYY'));
-- --usun_tych_co_zdali();
-- end;
--
-- -- znajdz pytania, jakie mial student w przeszlosci
-- select distinct zaw.id_zad
-- from podejscia p, zestawy zes, zawartosc zaw
-- where p.id_zes = zes.id_zes and zes.id_zes = zaw.id_zes
--     and p.indeks = id_studenta and (current_date - p.data_pod) YEAR TO MONTH < INTERVAL '1-6' YEAR TO MONTH;



create or replace procedure zaktualizuj_ocene1
(dataEgz in DATE) is
begin
    update studenci s
    set (s.ocena_1) = (
        select p.ocena
        from studenci ss join podejscia p on (ss.indeks = p.indeks)
        where s.indeks = ss.indeks)
    where s.indeks in (
        select ss.indeks
        from studenci ss, podejscia p, zestawy z
        where ss.indeks = p.indeks and z.id_zes = p.id_zes and
                z.data_egz = dataEgz and z.termin = 'pierwszy');

    update studenci s
    set (s.ocena_2) = (
        select p.ocena
        from studenci ss join podejscia p on (ss.indeks = p.indeks)
        where s.indeks = ss.indeks)
    where s.indeks in (
        select ss.indeks
        from studenci ss, podejscia p, zestawy z
        where ss.indeks = p.indeks and z.id_zes = p.id_zes and
                z.data_egz = dataEgz and z.termin != 'pierwszy');
end zaktualizuj_ocene1;

begin
    zaktualizuj_ocene1(to_date('04-02-2020','DD-MM-YYYY'));
    commit;
    --usun_tych_co_zdali();
end;