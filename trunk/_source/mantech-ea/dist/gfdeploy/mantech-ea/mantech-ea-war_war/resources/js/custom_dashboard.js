$(document).ready(function() {
			var d1 = [];
			for (var i = 0; i <= 10; i += 1)
				d1.push([i, parseInt(Math.random() * 30)]);

			var d2 = [];
			for (var i = 0; i <= 10; i += 1)
				d2.push([i, parseInt(Math.random() * 30)]);

			var d3 = [];
			for (var i = 0; i <= 10; i += 1)
				d3.push([i, parseInt(Math.random() * 30)]);

			function plotWithOptionsbars() {
				var stack = 0, bars = true, lines = false, steps = false;
				$.plot($("#bargraph"), [ d1, d2, d3 ], {
					series: {
						stack: stack,
						lines: { show: lines, steps: steps },
						bars: { show: bars, barWidth: 0.6 }
					}
				});
			} 
			plotWithOptionsbars();
});