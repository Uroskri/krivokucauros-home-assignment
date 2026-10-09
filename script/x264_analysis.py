import subprocess
import re
import os
import csv
import openpyxl
from openpyxl.chart import LineChart, Reference


x264_path = "x264.exe"
input_file = "foreman-cif.yuv"
csv_file_name = "results.csv"
excel_file_name = "x264_qp_report.xlsx"


# ============================================================
# 1. Run x264 for QP 0-51 and save results to CSV
# ============================================================

with open(csv_file_name, "w", newline="") as csv_file:

    writer = csv.writer(csv_file)

    # CSV header
    writer.writerow(["QP", "FileSizeKB", "EncodingFPS"])

    for qp in range(52):

        print(f"\nRunning QP {qp}...")

        output_file = f"output_qp{qp}.264"

        command = [
            x264_path,
            "--qp", str(qp),
            "--qpmin", "0",
            "--input-res", "352x288",
            "-o", output_file,
            input_file
        ]

        result = subprocess.run(
            command,
            capture_output=True,
            text=True
        )

        # Check if x264 completed successfully
        if result.returncode != 0:
            print(f"x264 failed for QP {qp}")
            print(result.stderr)
            continue

        # Extract encoding FPS
        match = re.search(
            r"encoded \d+ frames, ([\d.]+) fps",
            result.stderr
        )

        if not match:
            print(f"Could not find encoding FPS for QP {qp}")
            continue

        encoding_fps = float(match.group(1))

        # Get actual output file size
        file_size_bytes = os.path.getsize(output_file)
        file_size_kb = file_size_bytes / 1024

        # Write result immediately to CSV
        writer.writerow([
            qp,
            file_size_kb,
            encoding_fps
        ])

        # Make sure the result is physically written to disk
        csv_file.flush()

        print(
            f"QP {qp}: "
            f"{file_size_kb:.2f} KB, "
            f"{encoding_fps:.2f} FPS"
        )


print("\nAll QP tests completed.")


# ============================================================
# 2. Create Excel report from CSV
# ============================================================

print("\nCreating Excel report...")


wb = openpyxl.Workbook()
ws = wb.active
ws.title = "Results"


# Read results from CSV
with open(csv_file_name, "r", newline="") as csv_file:

    reader = csv.reader(csv_file)

    for row in reader:

        # Header
        if row[0] == "QP":
            ws.append([
                "QP",
                "FileSizeKB",
                "FileSizeMB",
                "EncodingFPS"
            ])

        # Data
        else:
            qp = int(row[0])
            file_size_kb = float(row[1])
            file_size_mb = file_size_kb / 1024
            encoding_fps = float(row[2])

            ws.append([
                qp,
                file_size_kb,
                file_size_mb,
                encoding_fps
            ])


# Format numeric values
for row in ws.iter_rows(min_row=2):

    row[0].number_format = "0"
    row[1].number_format = "0.00"
    row[2].number_format = "0.00"
    row[3].number_format = "0.00"


# ============================================================
# 3. File Size chart
# ============================================================

file_size_chart = LineChart()

file_size_chart.title = "QP vs File Size"
file_size_chart.y_axis.title = "File Size (MB)"
file_size_chart.x_axis.title = "QP"

data = Reference(
    ws,
    min_col=3,
    min_row=1,
    max_row=ws.max_row
)

categories = Reference(
    ws,
    min_col=1,
    min_row=2,
    max_row=ws.max_row
)

file_size_chart.add_data(
    data,
    titles_from_data=True
)

file_size_chart.set_categories(categories)


# ============================================================
# 4. Encoding FPS chart
# ============================================================

fps_chart = LineChart()

fps_chart.title = "QP vs Encoding FPS"
fps_chart.y_axis.title = "Encoding FPS"
fps_chart.x_axis.title = "QP"

data = Reference(
    ws,
    min_col=4,
    min_row=1,
    max_row=ws.max_row
)

fps_chart.add_data(
    data,
    titles_from_data=True
)

fps_chart.set_categories(categories)


# Add both charts to the same sheet

file_size_chart.width = 18
file_size_chart.height = 9
file_size_chart.x_axis.tickLblSkip = 5

fps_chart.width = 18
fps_chart.height = 9
fps_chart.x_axis.tickLblSkip = 5

ws.add_chart(file_size_chart, "F2")
ws.add_chart(fps_chart, "F22")


# ============================================================
# 5. Save Excel report
# ============================================================

wb.save(excel_file_name)

print(f"Excel report created: {excel_file_name}")