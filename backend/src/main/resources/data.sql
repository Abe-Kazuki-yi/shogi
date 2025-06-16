-- players
INSERT INTO players (id, name, password) VALUES (1, '春日部フウリ', '514remote');

-- pieces
INSERT INTO pieces (id, name, promoted) VALUES (1, '王', NULL);
INSERT INTO pieces (id, name, promoted) VALUES (2, '金', NULL);
INSERT INTO pieces (id, name, promoted) VALUES (3, '銀', '成銀');
INSERT INTO pieces (id, name, promoted) VALUES (4, '桂', '成桂');
INSERT INTO pieces (id, name, promoted) VALUES (5, '香', '成香');
INSERT INTO pieces (id, name, promoted) VALUES (6, '飛', '龍');
INSERT INTO pieces (id, name, promoted) VALUES (7, '角', '馬');
INSERT INTO pieces (id, name, promoted) VALUES (8, '歩', 'と');

-- templates
INSERT INTO templates(id, player_id, name, play_first, available, context) VALUES (1, 1, '三間飛車', true, true, '石田流本組');
INSERT INTO templates(id, player_id, name, play_first, available, context) VALUES (2, 1, '雁木', false, true, '後手で相手が居飛車');
INSERT INTO templates(id, player_id, name, play_first, available, context) VALUES (3, 1, '玉頭位取り', false, true, '後手で相手が振り飛車');

-- lists
INSERT INTO lists(id, template_id, num, before_id, before_x, before_y, target_x, target_y, is_promoted)
VALUES (1, 1, 1, 0, 7, 7, 7, 6, false);
INSERT INTO lists(id, template_id, num, before_id, before_x, before_y, target_x, target_y, is_promoted)
VALUES (2, 1, 2, 1, 3, 3, 3, 4, false);