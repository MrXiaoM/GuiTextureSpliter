using spliter.Properties;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace spliter
{
    public partial class Form1 : Form
    {
        OpenFileDialog ofd = new OpenFileDialog();
        Image selectedImage = null;
        public Form1()
        {
            InitializeComponent();
            ofd.Title = "选择图片";
            ofd.Filter = "PNG图片|*.png";
            ofd.FilterIndex = 0;
            ofd.RestoreDirectory = true;
            textBox1_TextChanged(null, null);
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            var yTop = numericUpDown1.Value.ToString();
            var yBottom = (numericUpDown1.Value - 127).ToString();
            ComponentResourceManager resources = new ComponentResourceManager(typeof(Form1));

            labelTipsItemsAdder.Text = resources.GetString("labelTipsItemsAdder.Text")
                .Replace("${namespace}", textBox1.Text)
                .Replace("${name}", textBox2.Text)
                .Replace("${path}", textBox4.Text)
                .Replace("${font}", textBox3.Text)
                .Replace("${y_top}", yTop)
                .Replace("${y_bottom}", yBottom);
            labelTipsCraftEngine.Text = resources.GetString("labelTipsCraftEngine.Text")
                .Replace("${namespace}", textBox1.Text)
                .Replace("${ce_namespace}", textBox5.Text)
                .Replace("${name}", textBox2.Text)
                .Replace("${path}", textBox4.Text)
                .Replace("${font}", textBox3.Text)
                .Replace("${y_top}", yTop)
                .Replace("${y_bottom}", yBottom);

            txtConfigItemsAdder.Text = Resources.ia_config
                .Replace("${namespace}", textBox1.Text)
                .Replace("${name}", textBox2.Text)
                .Replace("${path}", textBox4.Text)
                .Replace("${font}", textBox3.Text)
                .Replace("${y_top}", yTop)
                .Replace("${y_bottom}", yBottom);
            txtConfigCraftEngine.Text = Resources.ce_config
                .Replace("${namespace}", textBox1.Text)
                .Replace("${ce_namespace}", textBox5.Text)
                .Replace("${name}", textBox2.Text)
                .Replace("${path}", textBox4.Text)
                .Replace("${font}", textBox3.Text)
                .Replace("${y_top}", yTop)
                .Replace("${y_bottom}", yBottom);
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.Activate();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (ofd.ShowDialog() == DialogResult.OK)
            {
                FileInfo fi = new FileInfo(ofd.FileName);
                Image image;
                try
                {
                    if (!fi.Exists) throw new Exception();
                    image = Image.FromFile(ofd.FileName);
                    if (image.Width != 512 || image.Height != 512)
                    {
                        MessageBox.Show("应该选择大小为 512x512 的图片", "提示", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                        return;
                    }
                }
                catch
                {
                    MessageBox.Show("你选择的文件不存在，或不是图片文件!", "提示", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }
                selectedImage = image;
                pictureBox1.BackgroundImage = image;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (selectedImage == null)
            {
                MessageBox.Show("未选择图片文件");
                return;
            }
            Bitmap bmp1 = copy(selectedImage, 0, 0, 256, 256);
            Bitmap bmp2 = copy(selectedImage, 256, 0, 256, 256);
            Bitmap bmp3 = copy(selectedImage, 0, 256, 256, 256);
            Bitmap bmp4 = copy(selectedImage, 256, 256, 256, 256);
            var name = Application.StartupPath + "\\" + textBox2.Text;
            if (!Directory.Exists(name))
            {
                Directory.CreateDirectory(name);
            }
            delete(name + "\\1.png");
            delete(name + "\\2.png");
            delete(name + "\\3.png");
            delete(name + "\\4.png");
            bmp1.Save(name + "\\1.png", System.Drawing.Imaging.ImageFormat.Png);
            bmp2.Save(name + "\\2.png", System.Drawing.Imaging.ImageFormat.Png);
            bmp3.Save(name + "\\3.png", System.Drawing.Imaging.ImageFormat.Png);
            bmp4.Save(name + "\\4.png", System.Drawing.Imaging.ImageFormat.Png);
            if (MessageBox.Show("切割并导出完成，是否打开导出目录", "成功", MessageBoxButtons.YesNo, MessageBoxIcon.Information) == DialogResult.Yes)
            {
                Process.Start("explorer.exe", "\"" + name + "\"");
            }
        }

        public void delete(string path)
        {
            if (File.Exists(path)) File.Delete(path);
        }

        public Bitmap copy(Image image, int x, int y, int width, int height)
        {
            Bitmap bmp = new Bitmap(width, height);
            Graphics g = Graphics.FromImage(bmp);
            g.CompositingQuality = System.Drawing.Drawing2D.CompositingQuality.Default;
            g.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.NearestNeighbor;
            g.PixelOffsetMode = System.Drawing.Drawing2D.PixelOffsetMode.Default;
            g.DrawImage(image, -x, -y);
            return bmp;
        }
    }
}
