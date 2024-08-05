INSERT INTO stock (name, description, current_price, last_update) VALUES
                                                                          ('AAPL', 'Apple Inc.', 145.09, CURRENT_TIMESTAMP),
                                                                          ('GOOGL', 'Alphabet Inc.', 2738.80, CURRENT_TIMESTAMP),
                                                                          ('AMZN', 'Amazon.com Inc.', 3342.88, CURRENT_TIMESTAMP),
                                                                          ('MSFT', 'Microsoft Corporation', 299.35, CURRENT_TIMESTAMP),
                                                                          ('TSLA', 'Tesla Inc.', 650.57, CURRENT_TIMESTAMP),
                                                                          ('FB', 'Meta Platforms Inc.', 360.25, CURRENT_TIMESTAMP),
                                                                          ('NFLX', 'Netflix Inc.', 590.75, CURRENT_TIMESTAMP),
                                                                          ('NVDA', 'NVIDIA Corporation', 208.31, CURRENT_TIMESTAMP),
                                                                          ('ADBE', 'Adobe Inc.', 620.51, CURRENT_TIMESTAMP),
                                                                          ('PYPL', 'PayPal Holdings Inc.', 275.85, CURRENT_TIMESTAMP);

INSERT INTO stock_exchange (id, name, description, live_in_market) VALUES
                                                                       (1, 'NYSE', 'New York Stock Exchange', false),
                                                                       (2, 'NASDAQ', 'NASDAQ Stock Market', true),
                                                                       (3, 'LSE', 'London Stock Exchange', false),
                                                                       (4, 'JPX', 'Japan Exchange Group', false),
                                                                       (5, 'SSE', 'Shanghai Stock Exchange', false);

INSERT INTO stockexchange_stock (stockexchange_id, stock_id) VALUES
                                                                 (1, 1),
                                                                 (1, 2),
                                                                 (1, 3),
                                                                 (1, 4),
                                                                 (2, 1),
                                                                 (2, 5),
                                                                 (2, 6),
                                                                 (2, 7),
                                                                 (2, 8),
                                                                 (3, 4),
                                                                 (3, 9),
                                                                 (3, 10),
                                                                 (4, 2),
                                                                 (4, 5),
                                                                 (5, 3),
                                                                 (5, 6),
                                                                 (5, 7);