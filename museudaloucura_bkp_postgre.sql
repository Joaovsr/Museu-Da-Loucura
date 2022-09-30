--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

-- Started on 2022-02-13 21:50:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 73973)
-- Name: agendamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agendamento (
    id bigint NOT NULL,
    data date,
    email character varying(255),
    hash character varying(255),
    horario character varying(255) NOT NULL,
    numerodepessoas integer NOT NULL,
    CONSTRAINT agendamento_numerodepessoas_check CHECK (((numerodepessoas >= 1) AND (numerodepessoas <= 2)))
);


ALTER TABLE public.agendamento OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 73982)
-- Name: agendamento_pessoa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agendamento_pessoa (
    agendamento_id bigint NOT NULL,
    pessoas_id bigint NOT NULL
);


ALTER TABLE public.agendamento_pessoa OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 73967)
-- Name: agendamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.agendamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.agendamento_seq OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 73985)
-- Name: pessoa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa (
    id bigint NOT NULL,
    compareceu boolean NOT NULL,
    cpf character varying(255),
    nome character varying(255) NOT NULL,
    tipodoingresso character varying(255) NOT NULL
);


ALTER TABLE public.pessoa OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 73969)
-- Name: pessoa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pessoa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pessoa_seq OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 73993)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    login character varying(255),
    senha character varying(255)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 73971)
-- Name: usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_seq OWNER TO postgres;

--
-- TOC entry 3013 (class 0 OID 73973)
-- Dependencies: 203
-- Data for Name: agendamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agendamento (id, data, email, hash, horario, numerodepessoas) FROM stdin;
\.


--
-- TOC entry 3014 (class 0 OID 73982)
-- Dependencies: 204
-- Data for Name: agendamento_pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agendamento_pessoa (agendamento_id, pessoas_id) FROM stdin;
\.


--
-- TOC entry 3015 (class 0 OID 73985)
-- Dependencies: 205
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pessoa (id, compareceu, cpf, nome, tipodoingresso) FROM stdin;
\.


--
-- TOC entry 3016 (class 0 OID 73993)
-- Dependencies: 206
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id, login, senha) FROM stdin;
1	admin	admin
\.


--
-- TOC entry 3022 (class 0 OID 0)
-- Dependencies: 200
-- Name: agendamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.agendamento_seq', 1, false);


--
-- TOC entry 3023 (class 0 OID 0)
-- Dependencies: 201
-- Name: pessoa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pessoa_seq', 1, false);


--
-- TOC entry 3024 (class 0 OID 0)
-- Dependencies: 202
-- Name: usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_seq', 1, false);


--
-- TOC entry 2871 (class 2606 OID 73981)
-- Name: agendamento agendamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agendamento
    ADD CONSTRAINT agendamento_pkey PRIMARY KEY (id);


--
-- TOC entry 2875 (class 2606 OID 73992)
-- Name: pessoa pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (id);


--
-- TOC entry 2873 (class 2606 OID 74002)
-- Name: agendamento_pessoa uk_29e75dny4h0g8sgepmd7la3y2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agendamento_pessoa
    ADD CONSTRAINT uk_29e75dny4h0g8sgepmd7la3y2 UNIQUE (pessoas_id);


--
-- TOC entry 2877 (class 2606 OID 74000)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2878 (class 2606 OID 74003)
-- Name: agendamento_pessoa fk92k6fxocpx8cu64ldv8hhsayd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agendamento_pessoa
    ADD CONSTRAINT fk92k6fxocpx8cu64ldv8hhsayd FOREIGN KEY (pessoas_id) REFERENCES public.pessoa(id);


--
-- TOC entry 2879 (class 2606 OID 74008)
-- Name: agendamento_pessoa fk97f1kcbtg2gul5l5d76nkxxa6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agendamento_pessoa
    ADD CONSTRAINT fk97f1kcbtg2gul5l5d76nkxxa6 FOREIGN KEY (agendamento_id) REFERENCES public.agendamento(id);


-- Completed on 2022-02-13 21:50:10

--
-- PostgreSQL database dump complete
--

