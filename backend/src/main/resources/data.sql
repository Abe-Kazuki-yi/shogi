-- players
INSERT INTO players (id, name, password) VALUES (1, '春日部フウリ', '514remote');

-- pieces
INSERT INTO pieces (id, name, promoted_name, promoted) VALUES (1, '王', NULL, false);
INSERT INTO pieces (id, name, promoted_name, promoted) VALUES (2, '金', NULL, false);
INSERT INTO pieces (id, name, promoted_name, promoted) VALUES (3, '銀', '成銀', false);
INSERT INTO pieces (id, name, promoted_name, promoted) VALUES (4, '桂', '成桂', false);
INSERT INTO pieces (id, name, promoted_name, promoted) VALUES (5, '香', '成香', false);
INSERT INTO pieces (id, name, promoted_name, promoted) VALUES (6, '飛', '龍', false);
INSERT INTO pieces (id, name, promoted_name, promoted) VALUES (7, '角', '馬', false);
INSERT INTO pieces (id, name, promoted_name, promoted) VALUES (8, '歩', 'と', false);

-- templates
INSERT INTO templates(id, player_id, name, play_first, available, content) VALUES (1, 1, '三間飛車', true, true, '石田流本組を狙う');
INSERT INTO templates(id, player_id, name, play_first, available, content) VALUES (2, 1, '雁木', false, true, '相手が居飛車');
INSERT INTO templates(id, player_id, name, play_first, available, content) VALUES (3, 1, '玉頭位取り', false, true, '相手が振り飛車');
/*INSERT INTO templates(id, player_id, name, play_first, available, content) VALUES (4, 1, '棒銀', true, true, '最強');*/

-- lists
INSERT INTO lists(id, template_id, num, before_id, before_x, before_y, target_x, target_y, is_promoted)
VALUES (1, 1, 1, 0, 7, 7, 7, 6, false);
INSERT INTO lists(id, template_id, num, before_id, before_x, before_y, target_x, target_y, is_promoted)
VALUES (2, 1, 2, 1, 3, 3, 3, 4, false);
/*INSERT INTO lists(id, template_id, num, before_id, before_x, before_y, target_x, target_y, is_promoted)
VALUES (3, 4, 1, 0, 2, 7, 2, 6, false);*/